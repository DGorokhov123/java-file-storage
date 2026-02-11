package ru.dgorokhov.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResponseDto {

    private List<Long> deletedFiles = new ArrayList<>();

    private Map<Long, String> errors = new HashMap<>();

}
