package com.angelokezimana.starter.auth.exception;


import jakarta.persistence.EntityNotFoundException;

public class TokenNotFoundException extends EntityNotFoundException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public static TokenNotFoundException forId(Long tokenId) {
        return new TokenNotFoundException("Token with " + tokenId + " not found");
    }
}
