package com.angelokezimana.starter.auth.service;

import com.angelokezimana.starter.auth.dto.ResetPasswordRequestDTO;

public interface PasswordResetService {
    void generatePasswordResetToken(String email);
    void resetPassword(String token, ResetPasswordRequestDTO request);
}
