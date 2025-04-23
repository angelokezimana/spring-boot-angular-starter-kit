package com.angelokezimana.starter.auth.service;

import com.angelokezimana.starter.auth.dto.ResetPasswordRequestDto;
import jakarta.mail.MessagingException;

import java.util.Locale;

public interface PasswordResetService {
    void generatePasswordResetToken(String email, Locale locale) throws MessagingException;
    void resetPassword(String token, ResetPasswordRequestDto request);
}
