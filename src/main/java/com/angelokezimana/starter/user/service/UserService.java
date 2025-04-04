package com.angelokezimana.starter.user.service;

import com.angelokezimana.starter.user.dto.UserDTO;
import com.angelokezimana.starter.user.dto.UserRequestDTO;
import com.angelokezimana.starter.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> getCurrentUser();
    UserDTO getCurrentUserDTO();
    Page<UserDTO> getAllUsers(Pageable pageable);
    UserDTO createUser(UserRequestDTO userRequestDTO);
    UserDTO getUser(Long userId);
    UserDTO updateUser(Long userId, UserRequestDTO userRequestDTO);
    void deleteUser(Long userId);
}
