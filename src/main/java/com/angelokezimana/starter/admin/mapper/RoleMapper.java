package com.angelokezimana.starter.admin.mapper;

import com.angelokezimana.starter.admin.dto.RoleDto;
import com.angelokezimana.starter.admin.model.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDto toRoleDTO(Role role) {
        return new RoleDto(
                role.getId(),
                role.getName(),
                PermissionMapper.toPermissionDTOList(role.getPermissions())
        );
    }

    public static Role toRole(RoleDto roleDTO) {
        return new Role(roleDTO.name());
    }

    public static Set<RoleDto> toRoleDTOList(Set<Role> roles) {
        return roles.stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toSet());
    }
}
