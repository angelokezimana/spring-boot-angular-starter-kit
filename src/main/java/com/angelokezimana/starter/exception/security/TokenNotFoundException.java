package com.angelokezimana.starter.exception.security;


import jakarta.persistence.EntityNotFoundException;

public class TokenNotFoundException extends EntityNotFoundException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public static TokenNotFoundException forId(Long tokenId) {
        return new TokenNotFoundException("Token with " + tokenId + " not found");
    }
}
