package com.angelokezimana.starter.user.dto;

import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.common.validation.unique.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRequestDTO(
        Long id,
        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is mandatory")
        @Unique(entityClass = User.class, fieldName = "email", message = "Email is already in use")
        String email,

        @NotNull(message = "Select at least one role")
        Set<Long> roleIds) {
}
