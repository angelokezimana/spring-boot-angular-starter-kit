package com.angelokezimana.posta.dto.blog;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostRequestDTO(
        @NotBlank(message = "Text is mandatory")
        String text,
        @NotBlank(message = "Image cover is mandatory")
        MultipartFile imageCover,
        List<MultipartFile> photos
) {
}
