package com.zevaguillo.virtualToken.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zevaguillo.virtualToken.dto.TokenDto;
import com.zevaguillo.virtualToken.persistence.entity.TokenEntity;
import com.zevaguillo.virtualToken.persistence.entity.UserEntity;
import com.zevaguillo.virtualToken.persistence.repository.TokenRepository;
import com.zevaguillo.virtualToken.persistence.repository.UserRepository;
import com.zevaguillo.virtualToken.service.ITokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public TokenDto generateToken(String client) {

        Optional<UserEntity> user = userRepository.findById(Long.parseLong(client));

        if (user.isPresent()) {
            Optional<TokenEntity> tokenExistente = tokenRepository.findByUserAndStatus(user.get(), "activo");

            if (tokenExistente.isPresent() && tokenExistente.get().getExpirationTime().isAfter(LocalDateTime.now())) {
                return new TokenDto(tokenExistente.get().getId(), tokenExistente.get().getToken(),
                        tokenExistente.get().getGenerationTime(), tokenExistente.get().getExpirationTime(),
                        tokenExistente.get().getStatus()); // Devuelve el token existente si aún es válido
            }

            // Generar un nuevo token de 6 dígitos
            String nuevoToken = String.valueOf((int) (Math.random() * 900000) + 100000);
            TokenEntity token = TokenEntity.builder()
                    .token(nuevoToken)
                    .user(user.get())
                    .generationTime(LocalDateTime.now())
                    .expirationTime(LocalDateTime.now().plusSeconds(60))
                    .status("activo")
                    .build();

            TokenEntity saveToken = tokenRepository.save(token);

            return new TokenDto(saveToken.getId(), nuevoToken, saveToken.getGenerationTime(),
                    saveToken.getExpirationTime(), saveToken.getStatus()); // Guardar el nuevo token
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }

    @Override
    public boolean claimToken(String client, String tokenStr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'claimToken'");
    }

}
