package com.angelokezimana.posta.dto.blog;

import java.time.LocalDateTime;

public record CommentWithPostDTO(Long id,
                                 String text,
                                 LocalDateTime publishedOn,
                                 PostDTO postDTO,
                                 AuthorDTO author) {
}
