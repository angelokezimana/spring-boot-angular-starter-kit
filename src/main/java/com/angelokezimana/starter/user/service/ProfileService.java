package com.angelokezimana.starter.user.service;

import com.angelokezimana.starter.user.dto.ChangePasswordRequestDTO;
import com.angelokezimana.starter.user.dto.ChangeProfileInfoRequestDTO;

public interface ProfileService {
    void changePassword(ChangePasswordRequestDTO request);
    void changeProfile(ChangeProfileInfoRequestDTO request);
}
