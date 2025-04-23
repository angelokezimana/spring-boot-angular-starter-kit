package com.angelokezimana.starter.admin.dto;

public record PermissionDto(Long id,
                            String resource,
                            String action) {
}
