package com.angelokezimana.starter.user.service;

import com.angelokezimana.starter.user.dto.ChangePasswordRequestDTO;
import com.angelokezimana.starter.user.dto.ChangeProfileInfoRequestDTO;
import com.angelokezimana.starter.user.dto.UserDTO;
import com.angelokezimana.starter.user.model.User;

import java.util.Optional;

public interface ProfileService {
    void changePassword(ChangePasswordRequestDTO request);
    void changeProfile(ChangeProfileInfoRequestDTO request);
    Optional<User> getCurrentUser();
    UserDTO getCurrentUserDTO();
}
