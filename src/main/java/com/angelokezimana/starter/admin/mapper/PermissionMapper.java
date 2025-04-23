package com.angelokezimana.starter.admin.mapper;

import com.angelokezimana.starter.admin.dto.PermissionDto;
import com.angelokezimana.starter.admin.model.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public class PermissionMapper {

    public static PermissionDto toPermissionDTO(Permission permission) {
        return new PermissionDto(
                permission.getId(),
                permission.getResource(),
                permission.getAction()
        );
    }

    public static Set<PermissionDto> toPermissionDTOList(Set<Permission> permissions) {
        return permissions.stream()
                .map(PermissionMapper::toPermissionDTO)
                .collect(Collectors.toSet());
    }
}
