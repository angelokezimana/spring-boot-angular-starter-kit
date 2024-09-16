package com.angelokezimana.posta.dto.blog;

import jakarta.validation.constraints.NotBlank;

public record PhotoPostDTO(
        Long id,

        @NotBlank(message = "Image is mandatory")
        String image) {
}
