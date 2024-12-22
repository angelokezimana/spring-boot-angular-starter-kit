package com.angelokezimana.posta.repository.security;

import com.angelokezimana.posta.entity.security.PasswordResetToken;
import com.angelokezimana.posta.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    List<PasswordResetToken> findByUser(User user);
}
