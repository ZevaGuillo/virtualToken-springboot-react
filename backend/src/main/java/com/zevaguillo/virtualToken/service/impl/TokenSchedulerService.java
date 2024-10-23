package com.zevaguillo.virtualToken.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zevaguillo.virtualToken.persistence.entity.TokenEntity;
import com.zevaguillo.virtualToken.persistence.repository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenSchedulerService {
     private final TokenRepository tokenRepository;

         /**
     * Método programado para actualizar el estado de los tokens caducados a "inactivo".
     * Este método se ejecuta periódicamente cada X tiempo.
     */
    @Scheduled(fixedRate = 60000) // Cada 1 minuto (60000 ms) o puedes definir un cron expression
    public void actualizarTokensExpirados() {
        // Busca todos los tokens "activos" cuya fecha de expiración ya ha pasado
        List<TokenEntity> tokensExpirados = tokenRepository.findAllByStatusAndExpirationTimeBefore("activo", LocalDateTime.now());

        // Actualiza el estado de los tokens a "inactivo"
        tokensExpirados.forEach(token -> {
            token.setStatus("inactivo");
            tokenRepository.save(token);
        });

        log.info("Se actualizaron {} tokens caducados a 'inactivo'", tokensExpirados.size());
    }
}
