package com.angelokezimana.starter.auth.service;


import com.angelokezimana.starter.auth.dto.AuthenticationRequestDTO;
import com.angelokezimana.starter.auth.dto.AuthenticationResponseDTO;
import com.angelokezimana.starter.auth.dto.RegisterRequestDTO;
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
