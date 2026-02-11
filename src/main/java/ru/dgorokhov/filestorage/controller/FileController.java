package ru.dgorokhov.filestorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import ru.dgorokhov.filestorage.dto.FileMetadataDto;
import ru.dgorokhov.filestorage.entity.FileMetadata;
import ru.dgorokhov.filestorage.mapper.FileMetadataMapper;
import ru.dgorokhov.filestorage.service.FileStorageService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FileMetadataDto> getAllFiles() {
        return fileStorageService.getAllFiles().stream()
                .map(FileMetadataMapper::toDto)
                .toList();
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FileMetadataDto> uploadFiles(
            @RequestParam("files") MultipartFile[] files
    ) {
        return Arrays.stream(files)
                .map(file -> FileMetadataMapper.toDto(fileStorageService.storeFile(file)))
                .toList();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id
    ) {
        FileMetadata fileMetadata = fileStorageService.getFileById(id);
        Resource resource = fileStorageService.loadFileAsResource(fileMetadata.getStoredFileName());

        String encodedFileName = UriUtils.encode(fileMetadata.getOriginalFileName(), StandardCharsets.UTF_8);
        String contentDispositionValue = "attachment; filename*=UTF-8''" + encodedFileName;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileMetadata.getMimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionValue)
                .body(resource);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Long> deleteFiles(@RequestBody List<Long> fileIds) {
        return fileStorageService.deleteFiles(fileIds);
    }

}