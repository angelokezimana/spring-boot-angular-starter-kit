package com.angelokezimana.posta.dto.security;

import java.util.Set;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      boolean accountLocked,
                      boolean enabled,
                      Set<RoleDTO> roles) {
}
