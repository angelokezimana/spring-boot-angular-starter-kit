package com.angelokezimana.starter.auth.service.impl;

import com.angelokezimana.starter.auth.model.ActivationToken;
import com.angelokezimana.starter.auth.service.JwtService;
import com.angelokezimana.starter.common.dto.ResponseDTO;
import com.angelokezimana.starter.auth.dto.AuthenticationRequestDTO;
import com.angelokezimana.starter.auth.dto.AuthenticationResponseDTO;
import com.angelokezimana.starter.auth.dto.RegisterRequestDTO;
import com.angelokezimana.starter.common.email.EmailService;
import com.angelokezimana.starter.common.email.EmailTemplateName;
import com.angelokezimana.starter.role.exception.RoleNotFoundException;
import com.angelokezimana.starter.auth.exception.TokenNotFoundException;
import com.angelokezimana.starter.user.exception.UserNotFoundException;
import com.angelokezimana.starter.auth.repository.ActivationTokenRepository;
import com.angelokezimana.starter.role.repository.RoleRepository;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.user.repository.UserRepository;
import com.angelokezimana.starter.auth.service.AuthenticationService;
import com.angelokezimana.starter.auth.service.BlacklistedTokenService;
import com.angelokezimana.starter.role.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivationTokenRepository activationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final UserDetailsService userDetailsService;
    private final BlacklistedTokenService blacklistedTokenService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     ActivationTokenRepository activationTokenRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     EmailService emailService,
                                     UserDetailsService userDetailsService,
                                     BlacklistedTokenService blacklistedTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activationTokenRepository = activationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
        this.blacklistedTokenService = blacklistedTokenService;
    }

    public void register(RegisterRequestDTO request) throws MessagingException {

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("No role 'ROLE_USER' found"));

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setEnabled(false);
        user.setAccountLocked(false);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        sendValidationEmail(user);
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        User user = (User) auth.getPrincipal();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponseDTO(jwtToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    new ResponseDTO("error", "Refresh token is missing or invalid."));
            return;
        }

        final String refreshToken = authHeader.substring(7);
        final String extractRefreshToken = jwtService.extractExtraClaim(refreshToken, "token_type");

        final String userEmail = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        if (!extractRefreshToken.equals("refresh") ||
                !jwtService.isTokenValid(refreshToken, userDetails)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    new ResponseDTO("error", "Refresh token not valid or expired."));
            return;
        }

        // Retrieve user entity
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found."));

        // Blacklist the old refresh token
        blacklistedTokenService.saveBlacklistedToken(response, refreshToken, user);

        // Generate new tokens
        String accessToken = jwtService.generateToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        // Send response with new tokens
        AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO(accessToken, newRefreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        ActivationToken savedToken = activationTokenRepository.findByTokenAndValidatedAtIsNull(token)
                .orElseThrow(() -> new TokenNotFoundException("Invalid token"));

        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        long userId = savedToken.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        activationTokenRepository.save(savedToken);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getUsername(),
                user.getFirstName() + " " + user.getLastName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {

        String generatedToken = generateActivationCode(6);
        ActivationToken activationToken = new ActivationToken();
        activationToken.setToken(generatedToken);
        activationToken.setCreatedAt(LocalDateTime.now());
        activationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        activationToken.setUser(user);

        activationTokenRepository.save(activationToken);

        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
