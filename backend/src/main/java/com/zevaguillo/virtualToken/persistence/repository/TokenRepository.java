package com.zevaguillo.virtualToken.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zevaguillo.virtualToken.persistence.entity.TokenEntity;
import com.zevaguillo.virtualToken.persistence.entity.UserEntity;


public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByUserAndStatus(UserEntity user, String status);
}
