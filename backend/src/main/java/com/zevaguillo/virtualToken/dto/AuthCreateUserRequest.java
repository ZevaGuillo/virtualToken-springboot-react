package com.zevaguillo.virtualToken.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
public record AuthCreateUserRequest (
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email
) {
}
