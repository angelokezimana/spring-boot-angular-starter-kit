package com.angelokezimana.posta.dto.blog;

import java.time.LocalDateTime;

public record PostDTO(Long id,
                      String text,
                      byte[] imageCover,
                      LocalDateTime publishedOn,
                      Integer numberOfComments,
                      AuthorDTO author) {
}
