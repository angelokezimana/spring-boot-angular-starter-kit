package com.angelokezimana.starter.mapper.security;

import com.angelokezimana.starter.dto.security.UserDTO;
import com.angelokezimana.starter.entity.security.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.isAccountLocked(),
                user.isEnabled(),
                RoleMapper.toRoleDTOList(user.getRoles())
        );
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}

