package com.zevaguillo.virtualToken.exception;

import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = { JWTVerificationException.class })
    public ResponseEntity<ApiException> handleJWTVerificationException(JWTVerificationException e) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                "JWT Verification Error",
                "JWT verification failed: " + e.getMessage(),
                unauthorized);
        return new ResponseEntity<>(apiException, unauthorized);
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    public ResponseEntity<ApiException> handleAuthenticationException(AuthenticationException e) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        String message = "Authentication failed: " + e.getMessage();
        String details = "Authentication error. Please check your credentials.";

        if (e instanceof BadCredentialsException) {
            message = "Invalid credentials";
            details = "The username or password you entered is incorrect.";
        } else if (e instanceof UsernameNotFoundException) {
            message = "User not found";
            details = "The username you entered does not exist.";
        }

        ApiException apiException = new ApiException(
                message,
                details,
                unauthorized);
        return new ResponseEntity<>(apiException, unauthorized);
    }

    @ExceptionHandler(value = { JsonParseException.class })
    public ResponseEntity<ApiException> handleJsonParseException(JsonParseException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "JSON Parsing Error",
                "JSON parsing error: " + e.getMessage(),
                badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<ApiException> handleAccessDeniedException(AccessDeniedException e) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                "Access Denied",
                "You do not have the necessary permissions to access this resource.",
                forbidden);
        return new ResponseEntity<>(apiException, forbidden);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "Validation Error",
                "Validation failed. Please check your request fields and try again.",
                badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<ApiException> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "Request Body Error",
                "Request body is missing or invalid: " + e.getMessage(),
                badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    public ResponseEntity<ApiException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                "Data Integrity Violation",
                "An error occurred due to data integrity violation: " + e.getMostSpecificCause().getMessage(),
                conflict);
        return new ResponseEntity<>(apiException, conflict);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    public ResponseEntity<ApiException> handleNullPointerException(NullPointerException e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                "Null Pointer Exception",
                "An unexpected error occurred: " + e.getMessage(),
                internalServerError);
        return new ResponseEntity<>(apiException, internalServerError);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "Invalid Enum Value",
                "An unexpected error occurred: " + e.getMessage(),
                badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ApiException> handleGeneralException(Exception e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                "Internal Server Error",
                "An unexpected error occurred: " + e.getMessage(),
                internalServerError);
        return new ResponseEntity<>(apiException, internalServerError);
    }
}