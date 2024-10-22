package com.zevaguillo.virtualToken.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "message", "status", "user", "jwt" })
public record AuthResponse(UserResponse user,
        String message,
        String jwt,
        boolean status) {

}