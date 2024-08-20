package com.angelokezimana.posta.dto.security;

public record ChangePasswordRequestDTO(String currentPassword,
                                       String newPassword,
                                       String confirmationPassword) {
}
