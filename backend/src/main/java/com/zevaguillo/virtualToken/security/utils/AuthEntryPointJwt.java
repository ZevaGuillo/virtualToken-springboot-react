package com.zevaguillo.virtualToken.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zevaguillo.virtualToken.exception.ApiException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiException errorDetails = new ApiException(
                "Unauthorized",
                String.format("It was impossible to access this resource: %s. Please log in.", authException.getMessage()),
                HttpStatus.UNAUTHORIZED
        );

        // Use the static method `create` to construct an instance of `UserPrincipal`.
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorDetails);
    }
}