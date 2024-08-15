package com.angelokezimana.posta.dto.security;

import com.angelokezimana.posta.dto.blog.CommentWithPostDTO;
import com.angelokezimana.posta.dto.blog.PostDTO;

import java.util.List;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      List<PostDTO> posts,
                      List<CommentWithPostDTO> comments,
                      List<RoleDTO> roles) {
}
