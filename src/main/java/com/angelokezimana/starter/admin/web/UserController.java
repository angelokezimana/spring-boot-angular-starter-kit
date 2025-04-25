package com.angelokezimana.starter.admin.web;

import com.angelokezimana.starter.admin.service.UserService;
import com.angelokezimana.starter.common.dto.ResponseDto;
import com.angelokezimana.starter.user.dto.UserDto;
import com.angelokezimana.starter.user.dto.UserRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(required = false) String search
    ) {
        String sortBy = sort[0];
        String sortOrder = sort.length > 1 ? sort[1] : "asc";
        Sort parseSortParameter = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        Pageable pageable = PageRequest.of(page, size, parseSortParameter);
        Page<UserDto> userDTOs = userService.getAllUsers(search, pageable);

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping()
    private ResponseEntity<UserDto> create(@RequestBody @Valid UserRequestDto userRequestDTO) {

        UserDto userDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserDto> findById(@PathVariable Long userId) {

        UserDto postDTO = userService.getUser(userId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping(path = "/{userId}")
    private ResponseEntity<UserDto> update(@PathVariable Long userId,
                                           @ModelAttribute UserRequestDto updatedUser) {

        UserDto updatedUserResult = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(updatedUserResult);
    }

    @DeleteMapping("/{userId}")
    private ResponseEntity<ResponseDto> delete(@PathVariable Long userId) {

        userService.deleteUser(userId);
        return ResponseEntity.ok(new ResponseDto("message", "User deleted successfully"));
    }
}
