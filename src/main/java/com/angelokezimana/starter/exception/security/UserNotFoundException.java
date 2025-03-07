package com.angelokezimana.starter.exception.security;


import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException forId(Long userId) {
        return new UserNotFoundException("User with " + userId + " not found");
    }
}
