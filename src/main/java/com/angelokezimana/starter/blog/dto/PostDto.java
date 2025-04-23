package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;

public record PostDto(Long id,
                      String title,
                      String text,
                      byte[] imageCover,
                      LocalDateTime publishedOn,
                      Integer numberOfComments,
                      AuthorDto author) {
}
