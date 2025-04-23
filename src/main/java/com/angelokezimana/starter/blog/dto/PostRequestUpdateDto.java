package com.angelokezimana.starter.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record PostRequestUpdateDto(
        @NotBlank(message = "Title is mandatory")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,
        @NotBlank(message = "Text is mandatory")
        String text,
        MultipartFile imageCover
) {
}
