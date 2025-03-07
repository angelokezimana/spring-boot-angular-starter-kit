package com.angelokezimana.starter.service.security.impl;

import com.angelokezimana.starter.dto.security.ResetPasswordRequestDTO;
import com.angelokezimana.starter.email.EmailService;
import com.angelokezimana.starter.email.EmailTemplateName;
import com.angelokezimana.starter.entity.security.PasswordResetToken;
import com.angelokezimana.starter.entity.security.User;
import com.angelokezimana.starter.repository.security.PasswordResetTokenRepository;
import com.angelokezimana.starter.repository.security.UserRepository;
import com.angelokezimana.starter.service.security.PasswordResetService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.reset-password-url}")
    private String resetPasswordUrl;

    public PasswordResetServiceImpl(UserRepository userRepository,
                                    PasswordResetTokenRepository passwordResetTokenRepository,
                                    PasswordEncoder passwordEncoder,
                                    EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void generatePasswordResetToken(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {

            if (user.isEnabled() && user.isAccountNonLocked()) {

                List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findByUser(user);

                if (!passwordResetTokens.isEmpty()) {
                    passwordResetTokenRepository.deleteAll(passwordResetTokens);
                }

                String token = UUID.randomUUID().toString();
                PasswordResetToken resetToken = new PasswordResetToken(token, user);

                passwordResetTokenRepository.save(resetToken);

                String resetLink = resetPasswordUrl + "?token=" + token;
                try {
                    emailService.sendEmail(
                            user.getUsername(),
                            user.getFirstName() + " " + user.getLastName(),
                            EmailTemplateName.RESET_PASSWORD,
                            resetLink,
                            token,
                            "Password Reset Request");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void resetPassword(String token, ResetPasswordRequestDTO request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        // Invalidate the token after use
        passwordResetTokenRepository.delete(resetToken);
    }
}
