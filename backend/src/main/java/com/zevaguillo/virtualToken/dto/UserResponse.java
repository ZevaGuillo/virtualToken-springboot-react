package com.zevaguillo.virtualToken.dto;

public record UserResponse(
        Long id,
        String username,
        String email) {
}