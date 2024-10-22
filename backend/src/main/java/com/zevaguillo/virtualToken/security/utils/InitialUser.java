package com.zevaguillo.virtualToken.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zevaguillo.virtualToken.persistence.entity.UserEntity;
import com.zevaguillo.virtualToken.persistence.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class InitialUser {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Value("${initial.user.name}")
    private String username;

    @Value("${initial.user.email}")
    private String email;

    @Value("${initial.user.password}")
    private String password;

    @PostConstruct
    @Transactional
    public void initializeData() {
        try {

            // Create administrator user
            createAdminUser();

        } catch (Exception e) {
            throw new RuntimeException("Error when initializing the data: " + e.getMessage(), e);
        }
    }

    private void createAdminUser() {
        if (userRepository.findUserEntityByUsername(this.username).isEmpty()) {
            
            UserEntity userAdmin = UserEntity.builder()
                    .username(this.username)
                    .email(this.email)
                    .password(encoder.encode(this.password))  // Password must be encrypted
                    .build();

            userRepository.save(userAdmin);
        }
    }

}
