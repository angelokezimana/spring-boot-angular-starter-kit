package com.angelokezimana.starter.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank(message = "Text is mandatory")
        String text) {
}
