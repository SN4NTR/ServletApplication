package com.leverx.servletapp.user.dto.validator;

import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.dto.AnimalPointsDto;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class AnimalPointsValidator {

    public static final int ANIMAL_POINTS_MIN = 1;

    private static final String DELIMITER = "; ";

    public static void validateInputDto(AnimalPointsDto animalPointsDto) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(animalPointsDto);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }
    }

    private static String logErrors(Set<ConstraintViolation<AnimalPointsDto>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
