package com.angelokezimana.starter.mapper.blog;

import com.angelokezimana.starter.dto.blog.AuthorDTO;
import com.angelokezimana.starter.entity.security.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static AuthorDTO toAuthorDTO(User user) {
        return new AuthorDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername()
        );
    }

    public static List<AuthorDTO> toUserDTOList(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(AuthorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }
}
