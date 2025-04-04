package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;

public record CommentDTO(Long id,
                         String text,
                         LocalDateTime publishedOn,
                         AuthorDTO author) {
}
