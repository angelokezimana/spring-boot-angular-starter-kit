package com.angelokezimana.starter.common.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            // Use reflection to get the password and confirmPassword fields
            Field passwordFieldObject = obj.getClass().getDeclaredField(passwordField);
            Field confirmPasswordFieldObject = obj.getClass().getDeclaredField(confirmPasswordField);

            passwordFieldObject.setAccessible(true);
            confirmPasswordFieldObject.setAccessible(true);

            // Get the actual values of the fields
            String passwordValue = (String) passwordFieldObject.get(obj);
            String confirmPasswordValue = (String) confirmPasswordFieldObject.get(obj);

            // Check if both fields are non-null and if they match
            if (passwordValue != null && passwordValue.equals(confirmPasswordValue)) {
                return true;
            }

            // If passwords do not match, add a custom error message
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode(confirmPasswordField)
                    .addConstraintViolation();

            return false;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
