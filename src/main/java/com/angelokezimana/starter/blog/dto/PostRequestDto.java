package com.angelokezimana.starter.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostRequestDto(
        @NotBlank(message = "Title is mandatory")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,
        @NotBlank(message = "Text is mandatory")
        String text,
        @NotNull(message = "Image cover is mandatory")
        MultipartFile imageCover,
        List<MultipartFile> photos
) {
}
