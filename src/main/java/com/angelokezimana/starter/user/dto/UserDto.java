package com.angelokezimana.starter.user.dto;

import com.angelokezimana.starter.admin.dto.RoleDto;

import java.util.Set;

public record UserDto(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      boolean accountLocked,
                      boolean enabled,
                      Set<RoleDto> roles) {
}
