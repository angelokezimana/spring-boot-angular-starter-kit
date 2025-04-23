package com.angelokezimana.starter.user.service;

import com.angelokezimana.starter.user.dto.ChangePasswordRequestDto;
import com.angelokezimana.starter.user.dto.ChangeProfileInfoRequestDto;
import com.angelokezimana.starter.user.dto.UserDto;
import com.angelokezimana.starter.user.model.User;

import java.util.Optional;

public interface ProfileService {
    void changePassword(ChangePasswordRequestDto request);
    void changeProfile(ChangeProfileInfoRequestDto request);
    Optional<User> getCurrentUser();
    UserDto getCurrentUserDTO();
}
