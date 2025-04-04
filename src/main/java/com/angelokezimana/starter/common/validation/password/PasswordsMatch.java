package com.angelokezimana.starter.common.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {

    String message() default "Passwords do not match";

    String passwordField();

    String confirmPasswordField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
