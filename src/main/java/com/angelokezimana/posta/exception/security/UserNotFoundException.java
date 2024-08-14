package com.angelokezimana.posta.exception.security;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException forId(Long userId) {
        return new UserNotFoundException("User with " + userId + " not found");
    }
}
