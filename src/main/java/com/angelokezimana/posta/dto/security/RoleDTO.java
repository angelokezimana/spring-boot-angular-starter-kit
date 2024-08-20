package com.angelokezimana.posta.dto.security;

import java.util.Set;

public record RoleDTO(Long id,
                      String name,
                      Set<PermissionDTO> permissions) {
}
