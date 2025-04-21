package com.angelokezimana.starter.admin.service;

import com.angelokezimana.starter.user.dto.UserDTO;
import com.angelokezimana.starter.user.dto.UserRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    Page<UserDTO> getAllUsers(Pageable pageable);
    UserDTO createUser(UserRequestDTO userRequestDTO);
    UserDTO getUser(Long userId);
    UserDTO updateUser(Long userId, UserRequestDTO userRequestDTO);
    void deleteUser(Long userId);
}
