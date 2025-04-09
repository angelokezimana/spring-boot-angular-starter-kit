package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record PostDetailDTO(Long id,
                            String title,
                            String text,
                            byte[] imageCover,
                            LocalDateTime publishedOn,
                            Integer numberOfComments,
                            Set<PhotoPostDTO> photoPosts,
                            AuthorDTO author) {
}
