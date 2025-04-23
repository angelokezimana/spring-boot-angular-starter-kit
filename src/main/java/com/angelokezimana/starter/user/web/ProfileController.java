package com.angelokezimana.starter.user.web;

import com.angelokezimana.starter.common.dto.ResponseDto;
import com.angelokezimana.starter.user.dto.ChangePasswordRequestDto;
import com.angelokezimana.starter.user.dto.ChangeProfileInfoRequestDto;
import com.angelokezimana.starter.user.dto.UserDto;
import com.angelokezimana.starter.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody @Valid ChangePasswordRequestDto request) {

        profileService.changePassword(request);
        return ResponseEntity.ok(new ResponseDto("message", "Password changed successfully"));
    }

    @PostMapping("/change-profile")
    public ResponseEntity<ResponseDto> changeProfileInfo(@RequestBody ChangeProfileInfoRequestDto request) {

        profileService.changeProfile(request);
        return ResponseEntity.ok(new ResponseDto("message", "Info changed successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo() {

        UserDto user = profileService.getCurrentUserDTO();
        return ResponseEntity.ok(user);
    }
}
