package com.angelokezimana.starter.admin.dto;

import java.util.Set;

public record RoleDto(Long id,
                      String name,
                      Set<PermissionDto> permissions) {
}
