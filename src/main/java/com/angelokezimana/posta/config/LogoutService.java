package com.angelokezimana.posta.config;

import com.angelokezimana.posta.dto.ResponseDTO;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.repository.security.UserRepository;
import com.angelokezimana.posta.service.security.BlacklistedTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LogoutService implements LogoutHandler {

    private static final Logger log = LogManager.getLogger(LogoutService.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final BlacklistedTokenService blacklistedTokenService;

    @Autowired
    public LogoutService(JwtService jwtService,
                         UserDetailsService userDetailsService,
                         UserRepository userRepository,
                         BlacklistedTokenService blacklistedTokenService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.blacklistedTokenService = blacklistedTokenService;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new ResponseDTO("error", "Access token is missing or invalid."));
                return;
            }

            jwt = authHeader.substring(7);
            String accessToken = jwtService.extractExtraClaim(jwt, "token_type");

            String refreshToken = parseRefreshToken(request);
            if (refreshToken == null || refreshToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new ResponseDTO("error", "Refresh token is missing or invalid."));
                return;
            }

            String extractRefreshToken = jwtService.extractExtraClaim(refreshToken, "token_type");
            final String userEmail = jwtService.extractUsername(jwt);

            User authUser = (User) authentication.getPrincipal();

            if(authUser == null || !authUser.getUsername().equals(userEmail)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new ResponseDTO("error", "There is a problem with the authenticated user."));
                return;
            }

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (!accessToken.equals("access") ||
                    !extractRefreshToken.equals("refresh") ||
                    !jwtService.isTokenValid(refreshToken, userDetails)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new ResponseDTO("error", "Access or Refresh token not valid or expired."));
                return;
            }

            User user = userRepository.findByEmail(userEmail).orElseThrow();

            // Blacklist the refresh token
            blacklistedTokenService.saveBlacklistedToken(response, refreshToken, user);

            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    new ResponseDTO("success", "Logout successful."));
        } catch (Exception e) {
            log.error("Error during logout: ", e);
        }
    }

    private String parseRefreshToken(HttpServletRequest request) {
        try {
            var body = request.getReader().lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(body);
            return jsonNode.has("refresh") ? jsonNode.get("refresh").asText() : null;
        } catch (Exception e) {
            log.error("Failed to parse refresh token from body: ", e);
            return null;
        }
    }
}
