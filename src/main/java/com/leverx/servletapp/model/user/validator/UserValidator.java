package com.leverx.servletapp.model.user.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.user.dto.UserInputDto;
import com.leverx.servletapp.model.user.repository.UserRepository;
import com.leverx.servletapp.model.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class UserValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;
    public static final String DELIMITER = "; ";
    public static final String WRONG_NAME_SIZE_MSG = "First name must be between " + NAME_MIN_SIZE + " and " + NAME_MAX_SIZE;

    private static final UserRepository USER_REPOSITORY = new UserRepositoryImpl();
    private static final String USER_DOES_NOT_EXIST = "User doesn't exist";

    public static void validateInputDto(UserInputDto userInputDto) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(userInputDto);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }
    }

    public static void validateId(int id) throws EntityNotFoundException {
        var userOpt = USER_REPOSITORY.findById(id);
        userOpt.orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST, NOT_FOUND));
    }

    private static String logErrors(Set<ConstraintViolation<UserInputDto>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
