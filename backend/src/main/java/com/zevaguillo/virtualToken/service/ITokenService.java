package com.zevaguillo.virtualToken.service;

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
     * @param client - ID del cliente que está intentando usar el token
     * @param tokenStr - El token de 6 dígitos proporcionado por el cliente
     * @return boolean indicando si el uso del token fue exitoso o no
     */
    boolean claimToken(String client, String tokenStr);
}