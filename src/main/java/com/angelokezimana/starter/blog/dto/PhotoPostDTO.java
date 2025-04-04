package com.angelokezimana.starter.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record PhotoPostDTO(
        Long id,

        @NotBlank(message = "Image is mandatory")
        byte[] image) {
}
