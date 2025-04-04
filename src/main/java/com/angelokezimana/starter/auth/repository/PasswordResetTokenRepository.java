package com.angelokezimana.starter.auth.repository;

import com.angelokezimana.starter.auth.model.PasswordResetToken;
import com.angelokezimana.starter.user.model.User;
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
