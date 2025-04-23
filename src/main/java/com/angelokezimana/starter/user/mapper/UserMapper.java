package com.angelokezimana.starter.user.mapper;

import com.angelokezimana.starter.user.dto.UserDto;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.admin.mapper.RoleMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toUserDTO(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.isAccountLocked(),
                user.isEnabled(),
                RoleMapper.toRoleDTOList(user.getRoles())
        );
    }

    public static List<UserDto> toUserDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}

