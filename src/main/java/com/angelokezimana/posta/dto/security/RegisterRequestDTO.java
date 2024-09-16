package com.angelokezimana.posta.dto.security;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.passay.*;

import java.util.List;

public record RegisterRequestDTO(
        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotBlank(message = "Confirm Password is mandatory")
        String confirmPassword
) {
    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordsMatching() {
        return password != null && password.equals(confirmPassword);
    }

    @AssertTrue(message = "Password must contain at least 6 characters and cannot contain whitespace")
    public boolean isValidPassword() {
        if (password == null) {
            return false;
        }

        PasswordValidator validator = new PasswordValidator(
                List.of(
                        new LengthRule(6, 128),   // Password must be between 6 and 128 characters
                        new WhitespaceRule()      // No whitespace allowed in the password
                )
        );

        RuleResult result = validator.validate(new PasswordData(password));

        return result.isValid();
    }
}
