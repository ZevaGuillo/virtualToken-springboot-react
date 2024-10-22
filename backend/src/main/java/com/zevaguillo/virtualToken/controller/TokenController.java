package com.zevaguillo.virtualToken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zevaguillo.virtualToken.dto.TokenDto;
import com.zevaguillo.virtualToken.service.ITokenService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {
    private final ITokenService tokenService;

    @GetMapping("/generarToken")
    public TokenDto generateToken(@RequestParam @Valid @NotBlank(message = "El cliente no puede estar vacío") 
                                  @Pattern(regexp = "\\d+", message = "El cliente debe ser un número válido") String cliente) {
        return tokenService.generateToken(cliente);
    }

    @PostMapping("/usarToken")
    public boolean claimToken(@RequestParam String cliente, @RequestParam String token) {
        return tokenService.claimToken(cliente, token);
    }
}
