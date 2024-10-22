package com.zevaguillo.virtualToken.security.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zevaguillo.virtualToken.persistence.entity.UserEntity;
import com.zevaguillo.virtualToken.persistence.repository.UserRepository;
import com.zevaguillo.virtualToken.security.utils.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findUserEntityByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User not found with username: %s.", username)));

        return UserPrincipal.create(user);
    }
}
