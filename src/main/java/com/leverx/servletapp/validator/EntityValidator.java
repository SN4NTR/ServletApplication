package com.leverx.servletapp.validator;

import org.slf4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.slf4j.LoggerFactory.getLogger;

public class EntityValidator {

    private static final Logger LOGGER = getLogger(EntityValidator.class.getSimpleName());

    public static <T> boolean isEntityValid(T t) {
        ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(t);
        int violationsSize = violations.size();

        if (violationsSize == 0) {
            return true;
        } else {
            logErrors(violations);
            return false;
        }
    }

    private static <T> void logErrors(Set<ConstraintViolation<T>> violations) {
        for (ConstraintViolation<T> violation : violations) {
            LOGGER.error(violation.getMessage());
        }
    }
}
