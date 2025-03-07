package com.angelokezimana.starter.service.security;

import com.angelokezimana.starter.entity.security.User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface BlacklistedTokenService {
    void saveBlacklistedToken(HttpServletResponse response, String token, User user) throws IOException;
}
