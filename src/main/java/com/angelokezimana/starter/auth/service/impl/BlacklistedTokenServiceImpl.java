package com.angelokezimana.starter.auth.service.impl;

import com.angelokezimana.starter.auth.service.JwtService;
import com.angelokezimana.starter.common.dto.ResponseDto;
import com.angelokezimana.starter.auth.model.BlacklistedToken;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.auth.repository.BlacklistedTokenRepository;
import com.angelokezimana.starter.auth.service.BlacklistedTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class BlacklistedTokenServiceImpl implements BlacklistedTokenService {
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final JwtService jwtService;

    public BlacklistedTokenServiceImpl(BlacklistedTokenRepository blacklistedTokenRepository,
                                       JwtService jwtService) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
        this.jwtService = jwtService;
    }

    public void saveBlacklistedToken(HttpServletResponse response, String token, User user)throws IOException {

        BlacklistedToken storedRefreshToken = blacklistedTokenRepository.findByToken(token)
                .orElse(null);

        if (storedRefreshToken != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    new ResponseDto("error", "Refresh token already blacklisted."));
            return;
        }

        Date extractExpiryDateForRefreshToken = jwtService.extractExpiration(token);
        LocalDateTime refreshTokenExpiryDateTime = extractExpiryDateForRefreshToken.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(token);
        blacklistedToken.setBlacklistedAt(LocalDateTime.now());
        blacklistedToken.setCreatedAt(LocalDateTime.now());
        blacklistedToken.setExpiresAt(refreshTokenExpiryDateTime);
        blacklistedToken.setJti(jwtService.generateJti());
        blacklistedToken.setUser(user);
        blacklistedTokenRepository.save(blacklistedToken);
    }
}
