package com.leverx.servletapp.animal.validator;

import com.leverx.servletapp.animal.dto.AnimalInputDto;
import com.leverx.servletapp.animal.entity.Animal;
import com.leverx.servletapp.animal.repository.AnimalRepository;
import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.web.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.web.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.core.exception.ErrorConstant.ANIMAL_NOT_FOUND;
import static com.leverx.servletapp.core.exception.ErrorConstant.getLocalizedMessage;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class AnimalValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;

    private static final String DELIMITER = "; ";

    private AnimalRepository animalRepository;

    public void validateId(int id) throws EntityNotFoundException {
        var animalOpt = animalRepository.findById(id, Animal.class);
        var message = getLocalizedMessage(ANIMAL_NOT_FOUND);
        animalOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public static void validateInputDto(AnimalInputDto animalInputDto) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(animalInputDto);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }
    }

    private static String logErrors(Set<ConstraintViolation<AnimalInputDto>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
