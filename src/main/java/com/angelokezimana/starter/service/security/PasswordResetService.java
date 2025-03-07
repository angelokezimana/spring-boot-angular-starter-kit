package com.angelokezimana.starter.service.security;

import com.angelokezimana.starter.dto.security.ResetPasswordRequestDTO;

public interface PasswordResetService {
    void generatePasswordResetToken(String email);
    void resetPassword(String token, ResetPasswordRequestDTO request);
}
