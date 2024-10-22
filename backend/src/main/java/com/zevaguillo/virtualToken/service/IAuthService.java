package com.zevaguillo.virtualToken.service;

import com.zevaguillo.virtualToken.dto.AuthCreateUserRequest;
import com.zevaguillo.virtualToken.dto.AuthLoginRequest;
import com.zevaguillo.virtualToken.dto.AuthResponse;

public interface IAuthService {
    /**
     * Autentica a un usuario y devuelve un token JWT si la autenticación es
     * exitosa.
     *
     * @param authLoginRequest - Solicitud de inicio de sesión que contiene las
     *                         credenciales de usuario.
     * @return AuthResponse - Respuesta que contiene la información del usuario y el
     *         token JWT.
     */
    AuthResponse loginUser(AuthLoginRequest authLoginRequest);

    /**
     * Crea un nuevo usuario con las credenciales y roles especificados.
     *
     * @param authCreateUserRequest - Solicitud para crear un nuevo usuario, que incluye nombre de usuario, contraseña y roles.
     * @return AuthResponse - Respuesta que contiene la información del usuario creado y el token JWT generado.
     */
    AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest);
}
