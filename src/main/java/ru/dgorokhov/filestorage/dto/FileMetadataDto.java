package ru.dgorokhov.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadataDto {

    private Long id;
    private String fileName;
    private Long fileSize;
    private LocalDateTime uploadDate;
    private String mimeType;

}