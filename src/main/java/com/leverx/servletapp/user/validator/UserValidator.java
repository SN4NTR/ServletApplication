package com.leverx.servletapp.user.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.exception.constant.BundleConstant.MESSAGE_BUNDLE_NAME;
import static com.leverx.servletapp.exception.constant.BundleConstant.USER_NOT_FOUND;
import static com.leverx.servletapp.exception.constant.BundleConstant.getLocalizedMessage;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class UserValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;

    private static final String DELIMITER = "; ";
    private static final UserRepository USER_REPOSITORY;

    static {
        USER_REPOSITORY = new UserRepositoryImpl();
    }

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
        var message = getLocalizedMessage(MESSAGE_BUNDLE_NAME, USER_NOT_FOUND);
        userOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
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
