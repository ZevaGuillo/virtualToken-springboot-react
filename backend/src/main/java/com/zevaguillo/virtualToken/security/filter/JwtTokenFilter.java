package com.zevaguillo.virtualToken.security.filter;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zevaguillo.virtualToken.security.service.CustomUserDetailsService;
import com.zevaguillo.virtualToken.security.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        final String jwtToken = getJWTfromRequest(request);
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = jwtUtils.DecodedToken(jwtToken);
        String username = jwtUtils.extractUsername(decodedJWT);
        String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

        if (username != null) {
            UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);
            if (jwtUtils.validateJwtToken(jwtToken)) {
                Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList(stringAuthorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("JWT token is invalid for user: {}", username);
            }
        }

        // If no token is sent, continue with the following filter
        filterChain.doFilter(request, response);
    }

    private String getJWTfromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
