package com.angelokezimana.posta.controller.profile;

import com.angelokezimana.posta.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.posta.dto.profile.ChangeProfileInfoRequestDTO;
import com.angelokezimana.posta.service.security.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody @Valid ChangePasswordRequestDTO request) {

        Map<String, String> response = new HashMap<>();
        userService.changePassword(request);
        response.put("message", "Password changed successfully");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-profile")
    public ResponseEntity<Map<String, String>> changeProfileInfo(@RequestBody ChangeProfileInfoRequestDTO request) {

        Map<String, String> response = new HashMap<>();
        userService.changeProfile(request);
        response.put("message", "Info changed successfully");
        return ResponseEntity.ok(response);
    }
}
