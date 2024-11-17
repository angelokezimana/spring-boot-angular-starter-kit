package com.angelokezimana.posta.service.security.impl;

import com.angelokezimana.posta.dto.profile.ChangePasswordRequestDTO;
import com.angelokezimana.posta.dto.profile.ChangeProfileInfoRequestDTO;
import com.angelokezimana.posta.dto.security.UserDTO;
import com.angelokezimana.posta.entity.security.User;
import com.angelokezimana.posta.exception.security.UserNotFoundException;
import com.angelokezimana.posta.mapper.blog.PostMapper;
import com.angelokezimana.posta.mapper.security.UserMapper;
import com.angelokezimana.posta.repository.security.UserRepository;
import com.angelokezimana.posta.service.security.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return Optional.of((User) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    public void changePassword(ChangePasswordRequestDTO request) {

        User user = getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong current password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }

    public void changeProfile(ChangeProfileInfoRequestDTO request) {

        User user = getCurrentUser()
                .orElseThrow(() -> new UserNotFoundException("No authenticated user found"));

        updateFieldIfPresent(request.firstName(), user::setFirstName);
        updateFieldIfPresent(request.lastName(), user::setLastName);

        userRepository.save(user);
    }

    public UserDTO getCurrentUserDTO() {
        return UserMapper.toUserDTO(getCurrentUser().orElseThrow(() -> new IllegalStateException("User not found")));
    }

    private void updateFieldIfPresent(String newValue, Consumer<String> updateMethod) {
        if (newValue != null && !newValue.trim().isEmpty()) {
            updateMethod.accept(newValue);
        }
    }
}
