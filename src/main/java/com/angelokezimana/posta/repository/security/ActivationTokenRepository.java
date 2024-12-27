package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.entity.security.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    Optional<ActivationToken> findByTokenAndValidatedAtIsNull(String token);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

    long countByExpiresAtBefore(LocalDateTime expiresAt);
}

