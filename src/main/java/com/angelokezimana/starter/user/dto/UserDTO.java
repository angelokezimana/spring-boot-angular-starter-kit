package com.angelokezimana.starter.user.dto;

import com.angelokezimana.starter.admin.dto.RoleDTO;

import java.util.Set;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      boolean accountLocked,
                      boolean enabled,
                      Set<RoleDTO> roles) {
}
