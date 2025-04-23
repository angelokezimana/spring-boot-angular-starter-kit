package com.angelokezimana.starter.admin.service;


import com.angelokezimana.starter.admin.dto.RoleDto;
import com.angelokezimana.starter.admin.dto.RoleRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created By Angelo's on 3/7/2025.
 */
public interface RoleService {
    Page<RoleDto> getRoles(Pageable pageable);
    List<RoleDto> getAllRoles(String search);
    RoleDto createRole(RoleRequestDto roleRequestDTO);
    RoleDto getRole(Long roleId);
    RoleDto updateRole(Long roleId, RoleRequestDto roleRequestDTO);
    void deleteRole(Long roleId);
}
