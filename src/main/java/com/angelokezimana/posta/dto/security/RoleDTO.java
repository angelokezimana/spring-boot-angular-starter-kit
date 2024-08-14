package com.angelokezimana.posta.dto.security;

import java.util.List;

public record RoleDTO(Long id,
                      String name,
                      List<PermissionDTO> permissions) {
}
