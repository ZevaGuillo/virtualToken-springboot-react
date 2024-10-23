package com.zevaguillo.virtualToken.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zevaguillo.virtualToken.dto.TokenDto;

public interface ITokenService {

    /**
     * Genera un nuevo token o devuelve el token existente si aún es válido.
     *
     * @param client - ID del cliente para el cual se va a generar el token
     * @return Token generado o el token existente con tiempo de validez restante
     */
    TokenDto generateToken(String client);

    /**
     * Valida y usa el token proporcionado para un cliente específico.
     *
     * @param client   - ID del cliente que está intentando usar el token
     * @param tokenStr - El token de 6 dígitos proporcionado por el cliente
     * @return boolean indicando si el uso del token fue exitoso o no
     */
    boolean claimToken(String client, String tokenStr);

    /**
     * Obtiene una página de tokens filtrada según los criterios proporcionados.
     * 
     * @param userId    El ID del usuario cuyos tokens se van a buscar
     *                  (obligatorio).
     * @param token     El valor parcial o completo del token para buscar
     *                  .
     * @param startDate La fecha y hora de inicio para filtrar tokens generados
     *                  desde esa fecha.
     * @param endDate   La fecha y hora de fin para filtrar tokens generados hasta
     *                  esa fecha.
     * @param status    El estado de los tokens a buscar: "all" para todos, "used"
     *                  para los usados,
     *                  o "unused" para los no usados. Si es nulo, se toma como
     *                  "all".
     * @param pageable  Objeto de paginación para definir el tamaño de página,
     *                  número de página y
     *                  cualquier configuración adicional de paginación.
     * @return Una página que contiene los tokens filtrados.
     */
    Page<TokenDto> getAllTokensByFilters(Long userId, String token, LocalDateTime startDate, LocalDateTime endDate,
            String status,
            Pageable pageable);

}