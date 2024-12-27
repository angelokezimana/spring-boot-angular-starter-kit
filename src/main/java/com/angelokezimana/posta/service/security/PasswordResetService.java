package com.angelokezimana.posta.service.security;

import com.angelokezimana.posta.dto.security.ResetPasswordRequestDTO;

public interface PasswordResetService {
    void generatePasswordResetToken(String email);
    void resetPassword(String token, ResetPasswordRequestDTO request);
}
