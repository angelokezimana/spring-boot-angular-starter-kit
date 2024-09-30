package com.angelokezimana.posta.controller.profile;

import com.angelokezimana.posta.dto.ResponseDTO;
import com.angelokezimana.posta.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.posta.dto.profile.ChangeProfileInfoRequestDTO;
import com.angelokezimana.posta.service.security.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private static final Logger log = LogManager.getLogger(ProfileController.class);

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Valid ChangePasswordRequestDTO request) {

        userService.changePassword(request);
        return ResponseEntity.ok(new ResponseDTO("message", "Password changed successfully"));
    }

    @PatchMapping("/change-profile")
    public ResponseEntity<ResponseDTO> changeProfileInfo(@RequestBody ChangeProfileInfoRequestDTO request) {

        userService.changeProfile(request);
        return ResponseEntity.ok(new ResponseDTO("message", "Info changed successfully"));
    }
}
