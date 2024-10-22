package com.zevaguillo.virtualToken.security.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String privateKey;

    @Value("${jwt.user.generator}")
    private String userGenerator;

    @Value("${jwt.time.expiration}")
    private int expirationTime;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT DecodedToken(String token) throws JWTVerificationException {

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(this.userGenerator)
                .build();

        return verifier.verify(token);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            DecodedJWT decodedJWT = this.DecodedToken(authToken);
            return decodedJWT != null;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims empty: {}", e.getMessage());
        } catch (JWTVerificationException e) {
            log.error("Error verifying JWT: {}", e.getMessage());
        }
        return false;
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
