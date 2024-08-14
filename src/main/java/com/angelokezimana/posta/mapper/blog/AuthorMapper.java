package com.angelokezimana.posta.mapper.blog;

import com.angelokezimana.posta.dto.blog.AuthorDTO;
import com.angelokezimana.posta.entity.security.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static AuthorDTO toAuthorDTO(User user) {
        return new AuthorDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
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
