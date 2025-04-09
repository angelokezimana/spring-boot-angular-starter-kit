package com.angelokezimana.starter.user.service.impl;

import com.angelokezimana.starter.user.dto.UserDTO;
import com.angelokezimana.starter.user.dto.UserRequestDTO;
import com.angelokezimana.starter.role.model.Role;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.user.exception.UserNotFoundException;
import com.angelokezimana.starter.user.mapper.UserMapper;
import com.angelokezimana.starter.role.repository.RoleRepository;
import com.angelokezimana.starter.user.repository.UserRepository;
import com.angelokezimana.starter.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<User> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return Optional.of((User) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    public UserDTO getCurrentUserDTO() {
        return UserMapper.toUserDTO(getCurrentUser().orElseThrow(() -> new IllegalStateException("User not found")));
    }

    @PreAuthorize("hasPermission('USER', 'READ')")
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper::toUserDTO);
    }

    @PreAuthorize("hasPermission('USER', 'CREATE')")
    public UserDTO createUser(UserRequestDTO userRequestDTO) {

        User user = new User();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userRequestDTO.roleIds()));

        String passwordText = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        user.setFirstName(userRequestDTO.firstName());
        user.setLastName(userRequestDTO.lastName());
        user.setEmail(userRequestDTO.email());
        user.setPassword(passwordEncoder.encode(passwordText));
        user.setAccountLocked(false);
        user.setEnabled(true);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        logger.info("=================================Creating user======================");
        logger.info("Full name: {} {}. Email: {}. Password text: {}",
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                passwordText);
        logger.info("=================================Creating user======================");

        return UserMapper.toUserDTO(savedUser);
    }

    @PreAuthorize("hasPermission('USER', 'READ')")
    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));

        return UserMapper.toUserDTO(user);
    }

    @PreAuthorize("hasPermission('USER', 'UPDATE')")
    public UserDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userRequestDTO.roleIds()));

        existingUser.setFirstName(userRequestDTO.firstName());
        existingUser.setLastName(userRequestDTO.lastName());
        existingUser.setEmail(userRequestDTO.email());
        existingUser.setRoles(roles);

        User user = userRepository.save(existingUser);

        return UserMapper.toUserDTO(user);
    }

    @PreAuthorize("hasPermission('USER', 'DELETE')")
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));

        userRepository.delete(user);
    }
}
