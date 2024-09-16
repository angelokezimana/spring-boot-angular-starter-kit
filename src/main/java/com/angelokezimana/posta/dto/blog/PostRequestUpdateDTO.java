package com.angelokezimana.posta.dto.blog;

import jakarta.validation.constraints.NotBlank;

public record PostRequestUpdateDTO(
        @NotBlank(message = "Text is mandatory")
        String text
) {
}
