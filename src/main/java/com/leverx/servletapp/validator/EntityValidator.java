package com.leverx.servletapp.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;

@Slf4j
public class EntityValidator {

    public static <T> boolean isEntityValid(T t) {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();

        var violations = validator.validate(t);
        var violationsSize = violations.size();

        if (violationsSize == 0) {
            return true;
        } else {
            logErrors(violations);
            return false;
        }
    }

    private static <T> void logErrors(Set<ConstraintViolation<T>> violations) {
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(log::error);
    }
}
