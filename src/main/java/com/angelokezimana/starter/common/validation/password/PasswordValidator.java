package com.angelokezimana.starter.common.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import java.util.Arrays;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        org.passay.PasswordValidator validator = new org.passay.PasswordValidator(
                Arrays.asList(
                        // Password must be at least 6 characters long
                        new LengthRule(6, Integer.MAX_VALUE),
                        // Password must not contain whitespace
                        new WhitespaceRule()
                )
        );

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        // If password is invalid, customize the validation message
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                String.join(", ", validator.getMessages(result))
        ).addConstraintViolation();
        return false;
    }
}
