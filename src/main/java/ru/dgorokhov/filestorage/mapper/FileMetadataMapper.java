package ru.dgorokhov.filestorage.mapper;

import ru.dgorokhov.filestorage.dto.FileMetadataDto;
import ru.dgorokhov.filestorage.entity.FileMetadata;

public class FileMetadataMapper {

    public static FileMetadataDto toDto(FileMetadata fileMetadata) {
        return FileMetadataDto.builder()
                .id(fileMetadata.getId())
                .fileName(fileMetadata.getOriginalFileName())
                .fileSize(fileMetadata.getFileSize())
                .uploadDate(fileMetadata.getUploadDate())
                .mimeType(fileMetadata.getMimeType())
                .build();
    }

}
