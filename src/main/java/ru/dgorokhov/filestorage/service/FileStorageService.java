package ru.dgorokhov.filestorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dgorokhov.filestorage.entity.FileMetadata;
import ru.dgorokhov.filestorage.exception.NotFoundException;
import ru.dgorokhov.filestorage.repository.FileMetadataRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileMetadataRepository fileMetadataRepository;

    @Value("${file.storage.path}")
    private String fileStoragePath;

    public List<FileMetadata> getAllFiles() {
        return fileMetadataRepository.findAll();
    }

    public FileMetadata storeFile(MultipartFile file) {
        try {
            Path uploadDir = Paths.get(fileStoragePath);
            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.')) : "";
            String storedFileName = UUID.randomUUID() + fileExtension;
            Path filePath = uploadDir.resolve(storedFileName);

            Files.copy(file.getInputStream(), filePath);

            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setOriginalFileName(originalFileName);
            fileMetadata.setStoredFileName(storedFileName);
            fileMetadata.setFileSize(file.getSize());
            fileMetadata.setMimeType(file.getContentType());

            return fileMetadataRepository.save(fileMetadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource loadFileAsResource(String storedFileName) {
        Path filePath = Paths.get(fileStoragePath).resolve(storedFileName);

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) throw new NotFoundException("File not found: " + filePath);
            return resource;
        } catch (MalformedURLException e) {
            throw new NotFoundException("Can't load file: " + filePath);
        }
    }

    public FileMetadata getFileById(Long fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File metadata not found with id: " + fileId));
    }

    public List<Long> deleteFiles(List<Long> fileIds) {
        List<Long> res = new ArrayList<>();
        List<FileMetadata> filesToDelete = fileMetadataRepository.findAllById(fileIds);

        for (FileMetadata file : filesToDelete) {
            Path filePath = Paths.get(fileStoragePath).resolve(file.getStoredFileName());
            try {
                Files.deleteIfExists(filePath);
                res.add(file.getId());
            } catch (IOException e) {
                log.error("Can't delete file: {}", filePath);
            }
        }

        fileMetadataRepository.deleteAll(filesToDelete);
        return res;
    }

}