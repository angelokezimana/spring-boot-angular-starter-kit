package com.angelokezimana.starter.admin.web;

import com.angelokezimana.starter.common.dto.ResponseDTO;
import com.angelokezimana.starter.user.dto.UserDTO;
import com.angelokezimana.starter.user.dto.UserRequestDTO;
import com.angelokezimana.starter.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private ResponseEntity<List<UserDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<UserDTO> userDTOs = userService.getAllUsers(pageable);

        return ResponseEntity.ok(userDTOs.getContent());
    }

    @PostMapping()
    private ResponseEntity<UserDTO> create(@ModelAttribute @Valid UserRequestDTO userRequestDTO) {
        UserDTO userDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserDTO> findById(@PathVariable Long userId) {

        UserDTO postDTO = userService.getUser(userId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping(path = "/{userId}")
    private ResponseEntity<UserDTO> update(@PathVariable Long userId,
                                           @ModelAttribute UserRequestDTO updatedUser) {

        UserDTO updatedUserResult = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(updatedUserResult);
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<ResponseDTO> delete(@PathVariable Long userId) {

        userService.deleteUser(userId);
        return ResponseEntity.ok(new ResponseDTO("message", "User deleted successfully"));
    }
}
