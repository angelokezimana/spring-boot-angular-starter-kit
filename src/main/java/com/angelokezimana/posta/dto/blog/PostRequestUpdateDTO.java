package com.angelokezimana.posta.dto.blog;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record PostRequestUpdateDTO(
        @NotBlank(message = "Text is mandatory")
        String text,
        MultipartFile imageCover
) {
}
