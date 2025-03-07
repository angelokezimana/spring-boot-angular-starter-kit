package com.angelokezimana.starter.repository.security;

import com.angelokezimana.starter.entity.security.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    Optional<ActivationToken> findByTokenAndValidatedAtIsNull(String token);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

    long countByExpiresAtBefore(LocalDateTime expiresAt);
}

