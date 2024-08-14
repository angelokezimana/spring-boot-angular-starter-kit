package com.angelokezimana.posta.mapper.security;

import com.angelokezimana.posta.dto.security.PermissionDTO;
import com.angelokezimana.posta.entity.security.Permission;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionMapper {

    public static PermissionDTO toPermissionDTO(Permission permission) {
        return new PermissionDTO(
                permission.getId(),
                permission.getName()
        );
    }

    public static List<PermissionDTO> toPermissionDTOList(Collection<Permission> permissions) {
        return permissions.stream()
                .map(PermissionMapper::toPermissionDTO)
                .collect(Collectors.toList());
    }
}
