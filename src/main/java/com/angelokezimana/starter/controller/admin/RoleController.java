package com.angelokezimana.starter.controller.admin;

import com.angelokezimana.starter.dto.ResponseDTO;
import com.angelokezimana.starter.dto.security.RoleDTO;
import com.angelokezimana.starter.dto.security.RoleRequestDTO;
import com.angelokezimana.starter.service.security.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    private ResponseEntity<List<RoleDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<RoleDTO> roleDTOs = roleService.getRoles(pageable);

        return ResponseEntity.ok(roleDTOs.getContent());
    }

    @GetMapping("/all")
    private ResponseEntity<List<RoleDTO>> findAll(
            @RequestParam(defaultValue = "") String search) {

        List<RoleDTO> roleDTOs = roleService.getAllRoles(search);
        return ResponseEntity.ok(roleDTOs);
    }

    @PostMapping()
    private ResponseEntity<RoleDTO> create(@ModelAttribute @Valid RoleRequestDTO roleRequestDTO) {
        return ResponseEntity.ok(roleService.createRole(roleRequestDTO));
    }

    @GetMapping("/{roleId}")
    private ResponseEntity<RoleDTO> findById(@PathVariable Long roleId) {

        RoleDTO postDTO = roleService.getRole(roleId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping(path = "/{roleId}")
    private ResponseEntity<RoleDTO> update(@PathVariable Long roleId,
                                           @ModelAttribute RoleRequestDTO updatedRoleDTO) {

        RoleDTO updatedRoleResult = roleService.updateRole(roleId, updatedRoleDTO);
        return ResponseEntity.ok(updatedRoleResult);
    }

    @DeleteMapping("/{roleId}")
    private ResponseEntity<ResponseDTO> delete(@PathVariable Long roleId) {

        roleService.deleteRole(roleId);
        return ResponseEntity.ok(new ResponseDTO("message", "Role deleted successfully"));
    }
}
