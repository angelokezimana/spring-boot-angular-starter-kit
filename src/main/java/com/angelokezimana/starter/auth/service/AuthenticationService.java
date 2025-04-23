package com.angelokezimana.starter.auth.service;


import com.angelokezimana.starter.auth.dto.AuthenticationRequestDto;
import com.angelokezimana.starter.auth.dto.AuthenticationResponseDto;
import com.angelokezimana.starter.auth.dto.RegisterRequestDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

public interface AuthenticationService {
    void register(RegisterRequestDto request, Locale locale) throws MessagingException;
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void activateAccount(String token, Locale locale) throws MessagingException;
}
