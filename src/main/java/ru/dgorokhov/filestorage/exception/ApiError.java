package ru.dgorokhov.filestorage.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@Setter
@Getter
public class ApiError {

    Instant timestamp;

    HttpStatus status;

    String error;

    String message;

    String path;

}
