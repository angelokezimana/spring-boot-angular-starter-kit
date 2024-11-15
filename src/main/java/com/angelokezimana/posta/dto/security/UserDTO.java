package com.angelokezimana.posta.dto.security;

import com.angelokezimana.posta.dto.blog.CommentWithPostDTO;
import com.angelokezimana.posta.dto.blog.PostDTO;

import java.util.Set;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      Set<PostDTO> posts,
                      Set<CommentWithPostDTO> comments,
                      Set<RoleDTO> roles) {
}
