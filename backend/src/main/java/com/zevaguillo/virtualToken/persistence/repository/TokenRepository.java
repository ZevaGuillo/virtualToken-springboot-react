package com.zevaguillo.virtualToken.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zevaguillo.virtualToken.persistence.entity.TokenEntity;


public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
}
