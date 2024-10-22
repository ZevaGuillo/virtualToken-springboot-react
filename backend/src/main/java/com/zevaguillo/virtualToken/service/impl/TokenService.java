package com.zevaguillo.virtualToken.service.impl;

import org.springframework.stereotype.Service;

import com.zevaguillo.virtualToken.dto.TokenDto;
import com.zevaguillo.virtualToken.service.ITokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    
    @Override
    public TokenDto generateToken(String client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    @Override
    public boolean claimToken(String client, String tokenStr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'claimToken'");
    }

}
