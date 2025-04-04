package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;

public record PostDTO(Long id,
                      String title,
                      String text,
                      byte[] imageCover,
                      LocalDateTime publishedOn,
                      Integer numberOfComments,
                      AuthorDTO author) {
}
