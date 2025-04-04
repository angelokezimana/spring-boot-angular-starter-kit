package com.angelokezimana.starter.auth.repository;

import com.angelokezimana.starter.auth.model.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    Optional<BlacklistedToken> findByToken(String token);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

    long countByExpiresAtBefore(LocalDateTime expiresAt);
}
