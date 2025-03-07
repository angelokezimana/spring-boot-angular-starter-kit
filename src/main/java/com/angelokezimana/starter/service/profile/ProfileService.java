package com.angelokezimana.starter.service.profile;

import com.angelokezimana.starter.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.starter.dto.profile.ChangeProfileInfoRequestDTO;

public interface ProfileService {
    void changePassword(ChangePasswordRequestDTO request);
    void changeProfile(ChangeProfileInfoRequestDTO request);
}
