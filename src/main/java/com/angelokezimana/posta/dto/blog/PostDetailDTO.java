package com.angelokezimana.posta.dto.blog;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailDTO(Long id,
                            String text,
                            byte[] imageCover,
                            LocalDateTime publishedOn,
                            List<PhotoPostDTO> photoPosts,
                            AuthorDTO author) {
}
