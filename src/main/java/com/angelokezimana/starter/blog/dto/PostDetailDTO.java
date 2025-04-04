package com.angelokezimana.starter.blog.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailDTO(Long id,
                            String title,
                            String text,
                            byte[] imageCover,
                            LocalDateTime publishedOn,
                            Integer numberOfComments,
                            List<PhotoPostDTO> photoPosts,
                            AuthorDTO author) {
}
