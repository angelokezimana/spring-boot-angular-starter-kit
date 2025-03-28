package com.angelokezimana.starter.dto.blog;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDTO(
        @NotBlank(message = "Text is mandatory")
        String text) {
}
