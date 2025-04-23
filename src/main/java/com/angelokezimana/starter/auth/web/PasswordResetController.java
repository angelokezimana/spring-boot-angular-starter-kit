package com.angelokezimana.starter.auth.web;

import com.angelokezimana.starter.common.dto.ResponseDto;
import com.angelokezimana.starter.auth.dto.ResetPasswordRequestDto;
import com.angelokezimana.starter.auth.service.PasswordResetService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto> forgotPassword(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @RequestParam String email
    ) throws MessagingException {
        passwordResetService.generatePasswordResetToken(email, locale);
        return ResponseEntity.ok(new ResponseDto("message", "Password reset link sent to your email."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@RequestParam String token,
                                                     @RequestBody @Valid ResetPasswordRequestDto request) {
        passwordResetService.resetPassword(token, request);
        return ResponseEntity.ok(new ResponseDto("message", "Password has been reset successfully."));
    }
}
