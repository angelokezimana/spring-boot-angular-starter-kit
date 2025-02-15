/**
 * Created By angelokezimana
 * Date: 2/14/2025
 * Time: 2:22 PM
 * Project Name: posta
 */

package com.angelokezimana.posta.service.profile.impl;

import com.angelokezimana.posta.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.posta.dto.profile.ChangeProfileInfoRequestDTO;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.exception.security.UserNotFoundException;
import com.angelokezimana.posta.repository.security.UserRepository;
import com.angelokezimana.posta.service.profile.ProfileService;
import com.angelokezimana.posta.service.security.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void changePassword(ChangePasswordRequestDTO request) {

        User user = userService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong current password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }

    public void changeProfile(ChangeProfileInfoRequestDTO request) {

        User user = userService.getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        updateFieldIfPresent(request.firstName(), user::setFirstName);
        updateFieldIfPresent(request.lastName(), user::setLastName);

        userRepository.save(user);
    }

    private void updateFieldIfPresent(String newValue, Consumer<String> updateMethod) {
        if (newValue != null && !newValue.trim().isEmpty()) {
            updateMethod.accept(newValue);
        }
    }
}
