package com.angelokezimana.starter.auth.service;

import com.angelokezimana.starter.user.model.User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface BlacklistedTokenService {
    void saveBlacklistedToken(HttpServletResponse response, String token, User user) throws IOException;
}
