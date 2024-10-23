package com.zevaguillo.virtualToken.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zevaguillo.virtualToken.persistence.entity.TokenEntity;
import com.zevaguillo.virtualToken.persistence.entity.UserEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
        Optional<TokenEntity> findByUserAndStatus(UserEntity user, String status);

        @Query("SELECT t FROM TokenEntity t WHERE t.user.id = :userId AND " +
                        "(:token IS NULL OR t.token LIKE %:token%) AND " +
                        "t.generationTime BETWEEN :startDate AND :endDate")
        Page<TokenEntity> findAllByUserId(@Param("userId") Long userId,
                        @Param("token") String token,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate,
                        Pageable pageable);

        @Query("SELECT t FROM TokenEntity t WHERE t.user.id = :userId AND " +
                        "(:token IS NULL OR t.token LIKE %:token%) AND " +
                        "t.generationTime BETWEEN :startDate AND :endDate AND " +
                        "t.status = :status")
        Page<TokenEntity> findAllByUserIdAndStatus(@Param("userId") Long userId,
                        @Param("token") String token,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate,
                        @Param("status") String status,
                        Pageable pageable);
}
