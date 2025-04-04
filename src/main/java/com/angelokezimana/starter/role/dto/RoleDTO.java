package com.angelokezimana.starter.role.dto;

import java.util.Set;

public record RoleDTO(Long id,
                      String name,
                      Set<PermissionDTO> permissions) {
}
