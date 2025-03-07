package com.angelokezimana.starter.dto.security;

public record PermissionDTO(Long id,
                            String resource,
                            String action) {
}
