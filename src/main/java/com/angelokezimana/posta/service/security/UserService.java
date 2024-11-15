package com.angelokezimana.posta.service.security;

import com.angelokezimana.posta.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.posta.dto.profile.ChangeProfileInfoRequestDTO;
import com.angelokezimana.posta.dto.security.UserDTO;
import com.angelokezimana.posta.entity.security.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getCurrentUser();
    void changePassword(ChangePasswordRequestDTO request);
    void changeProfile(ChangeProfileInfoRequestDTO request);
    UserDTO getCurrentUserDTO();
}
