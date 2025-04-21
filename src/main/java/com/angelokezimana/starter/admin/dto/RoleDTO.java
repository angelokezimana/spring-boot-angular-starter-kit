package com.angelokezimana.starter.admin.dto;

import java.util.Set;

public record RoleDTO(Long id,
                      String name,
                      Set<PermissionDTO> permissions) {
}
