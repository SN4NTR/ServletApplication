package com.leverx.servletapp.model.animal.dog.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.animal.dog.dto.DogInputDto;
import com.leverx.servletapp.model.animal.dog.repository.DogRepository;
import com.leverx.servletapp.model.animal.dog.repository.DogRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class DogValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;
    public static final String DELIMITER = "; ";
    public static final String WRONG_DATE_MSG = "Date must be past or present";
    public static final String WRONG_NAME_SIZE_MSG = "First name must be between " + NAME_MIN_SIZE + " and " + NAME_MAX_SIZE;

    private static final String DOG_DOES_NOT_EXIST = "Dog doesn't exist";
    private static final DogRepository DOG_REPOSITORY = new DogRepositoryImpl();

    public static void validateInputDto(DogInputDto dogInputDto) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(dogInputDto);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }
    }

    public static void validateId(int id) throws EntityNotFoundException {
        var dogOpt = DOG_REPOSITORY.findById(id);
        dogOpt.orElseThrow(() -> new EntityNotFoundException(DOG_DOES_NOT_EXIST, UNPROCESSABLE_ENTITY));
    }

    public static void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var dogOpt = DOG_REPOSITORY.findById(id);
                dogOpt.orElseThrow(() -> new EntityNotFoundException(DOG_DOES_NOT_EXIST, UNPROCESSABLE_ENTITY));
            }
        }
    }

    private static String logErrors(Set<ConstraintViolation<DogInputDto>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
