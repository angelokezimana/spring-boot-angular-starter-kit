package com.angelokezimana.starter.auth.web;


import com.angelokezimana.starter.common.dto.ResponseDTO;
import com.angelokezimana.starter.auth.dto.AuthenticationRequestDTO;
import com.angelokezimana.starter.auth.dto.AuthenticationResponseDTO;
import com.angelokezimana.starter.auth.dto.RegisterRequestDTO;
import com.angelokezimana.starter.auth.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @RequestBody @Valid RegisterRequestDTO request
    ) throws MessagingException {

        authenticationService.register(request, locale);
        return ResponseEntity.ok(new ResponseDTO("message", "User created successfully"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestHeader(name = "Accept-Language", required = false) Locale locale,
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token, locale);
    }
}
