package com.zevaguillo.virtualToken.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zevaguillo.virtualToken.dto.AuthCreateUserRequest;
import com.zevaguillo.virtualToken.dto.AuthLoginRequest;
import com.zevaguillo.virtualToken.dto.AuthResponse;
import com.zevaguillo.virtualToken.dto.UserResponse;
import com.zevaguillo.virtualToken.persistence.entity.UserEntity;
import com.zevaguillo.virtualToken.persistence.repository.UserRepository;
import com.zevaguillo.virtualToken.security.utils.JwtUtils;
import com.zevaguillo.virtualToken.security.utils.UserPrincipal;
import com.zevaguillo.virtualToken.service.IAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        UserEntity userEntity = getUserByUsername(username);
        log.info("Intentando autenticar al usuario: {}", userEntity.getUsername());

        Authentication authentication = authenticate(userEntity, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        UserResponse userResponse = new UserResponse(userEntity.getId(), userEntity.getUsername(),
                userEntity.getEmail());

        return new AuthResponse(userResponse, "User Loged successfuly", accessToken, true);
    }

    @Override
    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        String email = authCreateUserRequest.email();

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        UserEntity userCreated = userRepository.save(userEntity);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), null,
                new ArrayList<>());
        String accessToken = jwtUtils.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponse userResponse = new UserResponse(userCreated.getId(), userCreated.getUsername(),
                userCreated.getEmail());

        return new AuthResponse(userResponse, "User created successfully", accessToken, true);
    }

    private Authentication authenticate(UserEntity user, String password) {

        UserDetails userDetails = UserPrincipal.create(user);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }

    private UserEntity getUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
