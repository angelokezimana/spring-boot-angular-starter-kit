package com.angelokezimana.posta.service.profile;

import com.angelokezimana.posta.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.posta.dto.profile.ChangeProfileInfoRequestDTO;

public interface ProfileService {
    void changePassword(ChangePasswordRequestDTO request);
    void changeProfile(ChangeProfileInfoRequestDTO request);
}
