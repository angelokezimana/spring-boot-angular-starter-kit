package com.angelokezimana.starter.auth.web;

import com.angelokezimana.starter.common.dto.ResponseDTO;
import com.angelokezimana.starter.auth.dto.ResetPasswordRequestDTO;
import com.angelokezimana.starter.auth.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestParam String email) {
        passwordResetService.generatePasswordResetToken(email);
        return ResponseEntity.ok(new ResponseDTO("message", "Password reset link sent to your email."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token,
                                                     @RequestBody @Valid ResetPasswordRequestDTO request) {
        passwordResetService.resetPassword(token, request);
        return ResponseEntity.ok(new ResponseDTO("message", "Password has been reset successfully."));
    }
}
