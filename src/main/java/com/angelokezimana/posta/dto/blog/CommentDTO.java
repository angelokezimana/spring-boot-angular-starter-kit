package com.angelokezimana.posta.dto.blog;

import java.time.LocalDateTime;

public record CommentDTO(Long id,
                         String text,
                         LocalDateTime publishedOn,
                         AuthorDTO author) {
}
