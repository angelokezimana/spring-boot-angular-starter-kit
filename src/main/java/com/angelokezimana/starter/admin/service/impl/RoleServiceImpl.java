package com.angelokezimana.starter.admin.service.impl;


import com.angelokezimana.starter.admin.dto.RoleDto;
import com.angelokezimana.starter.admin.dto.RoleRequestDto;
import com.angelokezimana.starter.admin.model.Permission;
import com.angelokezimana.starter.admin.model.Role;
import com.angelokezimana.starter.admin.exception.RoleNotFoundException;
import com.angelokezimana.starter.admin.mapper.RoleMapper;
import com.angelokezimana.starter.admin.repository.PermissionRepository;
import com.angelokezimana.starter.admin.repository.RoleRepository;
import com.angelokezimana.starter.admin.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created By Angelo's on 3/7/2025.
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @PreAuthorize("hasPermission('ROLE', 'READ')")
    public Page<RoleDto> getRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles.map(RoleMapper::toRoleDTO);
    }

    @PreAuthorize("hasPermission('ROLE', 'READ') || hasPermission('USER', 'CREATE')")
    public List<RoleDto> getAllRoles(String search) {

        List<Role> roles = roleRepository.findByNameContainingIgnoreCase(search);
        return roles.stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasPermission('ROLE', 'CREATE')")
    public RoleDto createRole(RoleRequestDto roleRequestDTO) {

        Role role = new Role();
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequestDTO.permissionIds()));

        role.setName(roleRequestDTO.name());
        role.setPermissions(permissions);

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toRoleDTO(savedRole);
    }

    @PreAuthorize("hasPermission('ROLE', 'READ')")
    public RoleDto getRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> RoleNotFoundException.forId(roleId));

        return RoleMapper.toRoleDTO(role);
    }

    @PreAuthorize("hasPermission('ROLE', 'UPDATE')")
    public RoleDto updateRole(Long roleId, RoleRequestDto roleRequestDTO) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> RoleNotFoundException.forId(roleId));

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleRequestDTO.permissionIds()));

        existingRole.setName(roleRequestDTO.name());
        existingRole.setPermissions(permissions);

        Role role = roleRepository.save(existingRole);

        return RoleMapper.toRoleDTO(role);
    }

    @PreAuthorize("hasPermission('ROLE', 'DELETE')")
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> RoleNotFoundException.forId(roleId));

        roleRepository.delete(role);
    }
}
