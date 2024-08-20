package com.angelokezimana.posta.dto.security;

public record RegisterRequestDTO(String firstName,
                                 String lastName,
                                 String email,
                                 String password,
                                 String confirmPassword) {
}
