package com.angelokezimana.posta.service.security.impl;

import com.angelokezimana.posta.dto.security.ResetPasswordRequestDTO;
import com.angelokezimana.posta.email.EmailService;
import com.angelokezimana.posta.email.EmailTemplateName;
import com.angelokezimana.posta.entity.security.PasswordResetToken;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.repository.security.PasswordResetTokenRepository;
import com.angelokezimana.posta.repository.security.UserRepository;
import com.angelokezimana.posta.service.security.PasswordResetService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {
    private static final Logger log = LogManager.getLogger(PasswordResetServiceImpl.class);

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

            log.debug("=======isEnabled={}=||==isAccountNonLocked={}======",
                    user.isEnabled(),
                    user.isAccountNonLocked());

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
