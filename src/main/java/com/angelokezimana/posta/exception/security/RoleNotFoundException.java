package com.angelokezimana.posta.exception.security;


import jakarta.persistence.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    public static RoleNotFoundException forId(Long roleId) {
        return new RoleNotFoundException("Role with " + roleId + " not found");
    }
}
