package com.zevaguillo.virtualToken.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zevaguillo.virtualToken.dto.TokenDto;
import com.zevaguillo.virtualToken.security.utils.CurrentUser;
import com.zevaguillo.virtualToken.security.utils.UserPrincipal;
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
    public TokenDto generateToken(
            @RequestParam @Valid @NotBlank(message = "El cliente no puede estar vacío") @Pattern(regexp = "\\d+", message = "El cliente debe ser un número válido") String cliente) {
        return tokenService.generateToken(cliente);
    }

    @PostMapping("/usarToken")
    public boolean claimToken(@RequestParam String cliente, @RequestParam String token) {
        return tokenService.claimToken(cliente, token);
    }

    @GetMapping("/all")
    public Page<TokenDto> getAllTokens(
            @RequestParam(required = false) String token,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "all") String status,
            @PageableDefault(size = 10) Pageable pageable,
            @CurrentUser UserPrincipal currentPrincipal) {
        return tokenService.getAllTokensByFilters(currentPrincipal.getId(), token, startDate, endDate, status,
                pageable);
    }

}
