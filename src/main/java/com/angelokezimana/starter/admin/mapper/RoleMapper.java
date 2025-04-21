package com.angelokezimana.starter.admin.mapper;

import com.angelokezimana.starter.admin.dto.RoleDTO;
import com.angelokezimana.starter.admin.model.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toRoleDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName(),
                PermissionMapper.toPermissionDTOList(role.getPermissions())
        );
    }

    public static Role toRole(RoleDTO roleDTO) {
        return new Role(roleDTO.name());
    }

    public static Set<RoleDTO> toRoleDTOList(Set<Role> roles) {
        return roles.stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toSet());
    }
}
