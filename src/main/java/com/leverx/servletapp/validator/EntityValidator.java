package com.leverx.servletapp.validator;

import com.leverx.servletapp.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static javax.validation.Validation.buildDefaultValidatorFactory;

@Slf4j
public class EntityValidator {

    private static final String DELIMITER = "; ";

    public static <T> void validateEntity(T t) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();

        var violations = validator.validate(t);
        if (violations.size() > 0) {
            var message = logErrors(violations);
            throw new ValidationException(message);
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
