package com.angelokezimana.starter.dto.security;

import com.angelokezimana.starter.entity.security.User;
import com.angelokezimana.starter.validation.password.PasswordsMatch;
import com.angelokezimana.starter.validation.password.ValidPassword;
import com.angelokezimana.starter.validation.unique.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@PasswordsMatch(passwordField = "password", confirmPasswordField = "confirmPassword", message = "Passwords must match")
public record RegisterRequestDTO(
        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is mandatory")
        @Unique(entityClass = User.class, fieldName = "email", message = "Email is already in use")
        String email,

        @NotBlank(message = "Password is mandatory")
        @ValidPassword(message = "Password must be at least 6 characters and must not contain whitespace")
        String password,

        @NotBlank(message = "Confirm Password is mandatory")
        String confirmPassword
) {
}
