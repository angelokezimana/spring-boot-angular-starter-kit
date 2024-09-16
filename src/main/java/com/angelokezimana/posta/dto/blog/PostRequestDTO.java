package com.angelokezimana.posta.dto.blog;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostRequestDTO(
        @NotBlank(message = "Text is mandatory")
        String text,

        List<PhotoPostDTO> photoPosts,
        AuthorDTO author
) {
}
