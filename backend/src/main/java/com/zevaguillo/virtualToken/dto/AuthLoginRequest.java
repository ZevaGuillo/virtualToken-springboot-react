package com.zevaguillo.virtualToken.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
public record AuthLoginRequest (@NotBlank String username,
                                @NotBlank String password) {
}
