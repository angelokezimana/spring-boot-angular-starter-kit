package com.angelokezimana.starter.auth.repository;

import com.angelokezimana.starter.auth.model.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    Optional<ActivationToken> findByTokenAndValidatedAtIsNull(String token);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

    long countByExpiresAtBefore(LocalDateTime expiresAt);
}

