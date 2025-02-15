package com.angelokezimana.posta.service.security;

import com.angelokezimana.posta.dto.security.UserDTO;
import com.angelokezimana.posta.dto.security.UserRequestDTO;
import com.angelokezimana.posta.entity.security.User;
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
