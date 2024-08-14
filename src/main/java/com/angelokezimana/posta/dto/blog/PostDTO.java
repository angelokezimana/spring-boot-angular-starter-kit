package com.angelokezimana.posta.dto.blog;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(Long id,
                      String text,
                      LocalDateTime publishedOn,
                      List<CommentDTO> comments,
                      List<PhotoPostDTO> photoPosts,
                      AuthorDTO author) {
}
