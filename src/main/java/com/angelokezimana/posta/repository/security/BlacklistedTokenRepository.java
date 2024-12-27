package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.entity.security.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    Optional<BlacklistedToken> findByToken(String token);

    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

    long countByExpiresAtBefore(LocalDateTime expiresAt);
}
