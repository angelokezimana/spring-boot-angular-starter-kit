package com.angelokezimana.posta.mapper.security;

import com.angelokezimana.posta.dto.security.UserDTO;
import com.angelokezimana.posta.entity.security.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                RoleMapper.toRoleDTOList(user.getRoles())
        );
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}

