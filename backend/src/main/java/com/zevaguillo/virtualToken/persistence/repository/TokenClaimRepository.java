package com.zevaguillo.virtualToken.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zevaguillo.virtualToken.persistence.entity.TokenClaimEntity;

public interface TokenClaimRepository extends JpaRepository<TokenClaimEntity, Long> {
}
