package com.angelokezimana.starter.mapper.security;

import com.angelokezimana.starter.dto.security.PermissionDTO;
import com.angelokezimana.starter.entity.security.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public class PermissionMapper {

    public static PermissionDTO toPermissionDTO(Permission permission) {
        return new PermissionDTO(
                permission.getId(),
                permission.getResource(),
                permission.getAction()
        );
    }

    public static Set<PermissionDTO> toPermissionDTOList(Set<Permission> permissions) {
        return permissions.stream()
                .map(PermissionMapper::toPermissionDTO)
                .collect(Collectors.toSet());
    }
}
