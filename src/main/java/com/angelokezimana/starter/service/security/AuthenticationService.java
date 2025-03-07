package com.angelokezimana.starter.service.security;


import com.angelokezimana.starter.dto.security.AuthenticationRequestDTO;
import com.angelokezimana.starter.dto.security.AuthenticationResponseDTO;
import com.angelokezimana.starter.dto.security.RegisterRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    void register(RegisterRequestDTO request) throws MessagingException;
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void activateAccount(String token) throws MessagingException;
}
