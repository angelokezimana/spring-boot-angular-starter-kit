package com.angelokezimana.posta.mapper.security;

import com.angelokezimana.posta.dto.security.RoleDTO;
import com.angelokezimana.posta.entity.security.Role;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toRoleDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName(),
                PermissionMapper.toPermissionDTOList(role.getPermissions())
        );
    }

    public static List<RoleDTO> toRoleDTOList(Set<Role> roles) {
        return roles.stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toList());
    }
}
