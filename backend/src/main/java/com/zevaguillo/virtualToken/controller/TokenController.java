package com.zevaguillo.virtualToken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zevaguillo.virtualToken.dto.TokenDto;
import com.zevaguillo.virtualToken.service.ITokenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {
    private final ITokenService tokenService;

    @GetMapping("/generate")
    public TokenDto generateToken(@RequestParam String cliente) {
        return tokenService.generateToken(cliente);
    }

    @PostMapping("/claim")
    public boolean claimToken(@RequestParam String cliente, @RequestParam String token) {
        return tokenService.claimToken(cliente, token);
    }
}
