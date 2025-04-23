package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;

public record CommentDto(Long id,
                         String text,
                         LocalDateTime publishedOn,
                         AuthorDto author) {
}
