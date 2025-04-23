package com.angelokezimana.starter.admin.service.impl;

import com.angelokezimana.starter.admin.service.UserService;
import com.angelokezimana.starter.admin.model.Role;
import com.angelokezimana.starter.admin.repository.RoleRepository;
import com.angelokezimana.starter.user.dto.UserDto;
import com.angelokezimana.starter.user.dto.UserRequestDto;
import com.angelokezimana.starter.user.exception.UserNotFoundException;
import com.angelokezimana.starter.user.mapper.UserMapper;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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

    @PreAuthorize("hasPermission('USER', 'READ')")
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllUsers(pageable);
        return users.map(UserMapper::toUserDTO);
    }

    @PreAuthorize("hasPermission('USER', 'CREATE')")
    public UserDto createUser(UserRequestDto userRequestDTO) {

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
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.forId(userId));

        return UserMapper.toUserDTO(user);
    }

    @PreAuthorize("hasPermission('USER', 'UPDATE')")
    public UserDto updateUser(Long userId, UserRequestDto userRequestDTO) {
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
