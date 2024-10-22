package com.zevaguillo.virtualToken.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException {
    private final String message;
    private final String details;
    private final HttpStatus httpStatus;
    private LocalDateTime time = LocalDateTime.now();

    public ApiException(String message, String details, HttpStatus httpStatus) {
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }
}