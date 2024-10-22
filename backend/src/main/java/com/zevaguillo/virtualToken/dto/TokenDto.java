package com.zevaguillo.virtualToken.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDto {
    private Long id;
    private String token;
    private LocalDateTime tiempoGeneracion;
    private LocalDateTime tiempoExpiracion;
    private String status;
}
