package com.angelokezimana.starter.user.web;

import com.angelokezimana.starter.common.dto.ResponseDTO;
import com.angelokezimana.starter.user.dto.ChangePasswordRequestDTO;
import com.angelokezimana.starter.user.dto.ChangeProfileInfoRequestDTO;
import com.angelokezimana.starter.user.dto.UserDTO;
import com.angelokezimana.starter.user.service.ProfileService;
import com.angelokezimana.starter.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Valid ChangePasswordRequestDTO request) {

        profileService.changePassword(request);
        return ResponseEntity.ok(new ResponseDTO("message", "Password changed successfully"));
    }

    @PatchMapping("/change-profile")
    public ResponseEntity<ResponseDTO> changeProfileInfo(@RequestBody ChangeProfileInfoRequestDTO request) {

        profileService.changeProfile(request);
        return ResponseEntity.ok(new ResponseDTO("message", "Info changed successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo() {

        UserDTO user = userService.getCurrentUserDTO();
        return ResponseEntity.ok(user);
    }
}
