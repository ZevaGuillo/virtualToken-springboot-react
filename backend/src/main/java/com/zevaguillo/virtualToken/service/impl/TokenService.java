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

    /**
     * Genera un nuevo token de autenticación para un usuario específico o devuelve
     * un token activo existente si aún es válido.
     *
     * @param client El ID del cliente, proporcionado como una cadena de texto.
     * @return Un objeto `TokenDto` que contiene la información del token generado o
     *         el token existente si aún es válido.
     * @throws UsernameNotFoundException Si el usuario no es encontrado en la base
     *                                   de datos.
     */
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

    /**
     * Valida y consume un token para un cliente específico. Si el token es válido,
     * se marca como "usado".
     * Si es inválido, se marca como "inactivo".
     *
     * @param client   El ID del cliente, proporcionado como una cadena de texto.
     * @param tokenStr El token que se intenta reclamar.
     * @return `true` si el token es válido y fue usado exitosamente, `false` en
     *         caso contrario.
     */
    @Override
    public boolean claimToken(String client, String tokenStr) {
        Optional<UserEntity> user = userRepository.findById(Long.parseLong(client));

        if (user.isPresent()) {
            Optional<TokenEntity> token = tokenRepository.findByUserAndStatus(user.get(), "activo");

            // Si el token es válido y no ha expirado, marcarlo como "usado"
            if (token.isPresent() && token.get().getToken().equals(tokenStr)
                    && token.get().getExpirationTime().isAfter(LocalDateTime.now())) {
                token.get().setStatus("usado");
                tokenRepository.save(token.get());

                return true;
            } else {
                // Si el token es inválido o ha expirado, marcarlo como "inactivo"
                token.get().setStatus("inactivo");
                tokenRepository.save(token.get());
            }
        }

        return false;
    }

}
