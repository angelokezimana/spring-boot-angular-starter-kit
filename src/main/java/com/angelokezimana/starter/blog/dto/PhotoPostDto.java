package com.angelokezimana.starter.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record PhotoPostDto(
        Long id,

        @NotBlank(message = "Image is mandatory")
        byte[] image) {
}
