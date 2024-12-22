package com.angelokezimana.posta.service.security;

import com.angelokezimana.posta.dto.security.ResetPasswordRequestDTO;
import jakarta.mail.MessagingException;

public interface PasswordResetService {
    void generatePasswordResetToken(String email);
    void resetPassword(String token, ResetPasswordRequestDTO request);
}
