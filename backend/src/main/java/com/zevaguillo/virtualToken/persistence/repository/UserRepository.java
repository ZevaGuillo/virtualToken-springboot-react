package com.zevaguillo.virtualToken.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zevaguillo.virtualToken.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}