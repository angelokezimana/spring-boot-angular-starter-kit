package com.angelokezimana.starter.common.validation.unique;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String fieldName;
    private String idFieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
        this.fieldName = constraintAnnotation.fieldName();
        this.idFieldName = constraintAnnotation.idFieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Leave null or empty checks to other annotations
        }

        // Build a dynamic query
        String queryStr = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s  = :value", entityClass.getSimpleName(), fieldName);
        TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
        query.setParameter("value", value);

        // Check if the field value already exists
        return query.getSingleResult() == 0;
    }
}
