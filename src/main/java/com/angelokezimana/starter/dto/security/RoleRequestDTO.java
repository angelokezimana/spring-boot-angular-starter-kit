package com.angelokezimana.starter.dto.security;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RoleRequestDTO(
        Long id,
        @NotBlank(message = "Name is mandatory")
        String name,
        Set<Long> permissionIds
) {
}
