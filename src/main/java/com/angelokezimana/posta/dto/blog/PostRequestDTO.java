package com.angelokezimana.posta.dto.blog;

import java.util.List;

public record PostRequestDTO(String text,
                             List<PhotoPostDTO> photoPosts,
                             AuthorDTO author) {
}
