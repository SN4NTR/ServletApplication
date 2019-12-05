package com.leverx.servletapp.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static javax.validation.Validation.buildDefaultValidatorFactory;

@Slf4j
public class EntityValidator {

    private static final String DELIMITER = ".\n";

    public static <T> void validateEntity(T t) throws IllegalArgumentException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();

        var violations = validator.validate(t);
        if (violations.size() > 0) {
            var message = logErrors(violations);
            throw new IllegalArgumentException(message);
        }
    }

    private static <T> String logErrors(Set<ConstraintViolation<T>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
