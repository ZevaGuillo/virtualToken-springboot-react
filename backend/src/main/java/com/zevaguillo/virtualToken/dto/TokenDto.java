package com.zevaguillo.virtualToken.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private Long id;
    private String token;
    private LocalDateTime tiempoGeneracion;
    private LocalDateTime tiempoExpiracion;
    private String estado;
}
