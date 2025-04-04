package com.angelokezimana.starter.common.validation.unique;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "Field value is already in use";

    Class<?> entityClass(); // Entity class, e.g., User, Role, etc.

    String fieldName();     // Field name, e.g., email, username, etc.

    String idFieldName() default "id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
