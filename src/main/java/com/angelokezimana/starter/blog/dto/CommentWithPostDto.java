package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;

public record CommentWithPostDto(Long id,
                                 String text,
                                 LocalDateTime publishedOn,
                                 PostDto post,
                                 AuthorDto author) {
}
