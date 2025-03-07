package com.angelokezimana.starter.repository.security;

import com.angelokezimana.starter.entity.security.PasswordResetToken;
import com.angelokezimana.starter.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    List<PasswordResetToken> findByUser(User user);

    void deleteByExpiryDateBefore(Date expiryDate);
    long countByExpiryDateBefore(Date expiryDate);
}
