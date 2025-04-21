package com.angelokezimana.starter.admin.service;


import com.angelokezimana.starter.admin.dto.RoleDTO;
import com.angelokezimana.starter.admin.dto.RoleRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created By Angelo's on 3/7/2025.
 */
public interface RoleService {
    Page<RoleDTO> getRoles(Pageable pageable);
    List<RoleDTO> getAllRoles(String search);
    RoleDTO createRole(RoleRequestDTO roleRequestDTO);
    RoleDTO getRole(Long roleId);
    RoleDTO updateRole(Long roleId, RoleRequestDTO roleRequestDTO);
    void deleteRole(Long roleId);
}
