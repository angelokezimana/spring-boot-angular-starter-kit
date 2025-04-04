package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;

public record CommentWithPostDTO(Long id,
                                 String text,
                                 LocalDateTime publishedOn,
                                 PostDTO post,
                                 AuthorDTO author) {
}
