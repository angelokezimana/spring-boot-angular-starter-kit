package com.angelokezimana.starter.admin.web;

import com.angelokezimana.starter.common.dto.ResponseDto;
import com.angelokezimana.starter.admin.dto.RoleDto;
import com.angelokezimana.starter.admin.dto.RoleRequestDto;
import com.angelokezimana.starter.admin.service.RoleService;
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
    private ResponseEntity<List<RoleDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<RoleDto> roleDTOs = roleService.getRoles(pageable);

        return ResponseEntity.ok(roleDTOs.getContent());
    }

    @GetMapping("/all")
    private ResponseEntity<List<RoleDto>> findAll(
            @RequestParam(defaultValue = "") String search) {

        List<RoleDto> roleDTOs = roleService.getAllRoles(search);
        return ResponseEntity.ok(roleDTOs);
    }

    @PostMapping()
    private ResponseEntity<RoleDto> create(@ModelAttribute @Valid RoleRequestDto roleRequestDTO) {
        return ResponseEntity.ok(roleService.createRole(roleRequestDTO));
    }

    @GetMapping("/{roleId}")
    private ResponseEntity<RoleDto> findById(@PathVariable Long roleId) {

        RoleDto postDTO = roleService.getRole(roleId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping(path = "/{roleId}")
    private ResponseEntity<RoleDto> update(@PathVariable Long roleId,
                                           @ModelAttribute RoleRequestDto updatedRoleDTO) {

        RoleDto updatedRoleResult = roleService.updateRole(roleId, updatedRoleDTO);
        return ResponseEntity.ok(updatedRoleResult);
    }

    @DeleteMapping("/{roleId}")
    private ResponseEntity<ResponseDto> delete(@PathVariable Long roleId) {

        roleService.deleteRole(roleId);
        return ResponseEntity.ok(new ResponseDto("message", "Role deleted successfully"));
    }
}
