package com.leverx.servletapp.user.validator;

import com.leverx.servletapp.cat.validator.CatValidator;
import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.dog.validator.DogValidator;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserTransferDto;
import com.leverx.servletapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.core.exception.ErrorConstant.USER_NOT_FOUND;
import static com.leverx.servletapp.core.exception.ErrorConstant.getLocalizedMessage;
import static com.leverx.servletapp.web.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.web.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class UserValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;
    public static final int ANIMAL_POINTS_MIN = 1;

    private static final String DELIMITER = "; ";

    private UserRepository userRepository;
    private CatValidator catValidator;
    private DogValidator dogValidator;

    public void validateId(int id) throws EntityNotFoundException {
        var userOpt = userRepository.findById(id);
        var message = getLocalizedMessage(USER_NOT_FOUND);
        userOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public void validateInputDto(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        validateDto(userInputDto);
        var catsIds = userInputDto.getCatsIds();
        catValidator.validateIds(catsIds);
        var dogsIds = userInputDto.getDogsIds();
        dogValidator.validateIds(dogsIds);
    }

    public void validateTransferDto(UserTransferDto userTransferDto) throws ValidationException, EntityNotFoundException {
        validateDto(userTransferDto);
        var receiverId = userTransferDto.getReceiverId();
        validateId(receiverId);
    }

    private <T> void validateDto(T t) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(t);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }
    }

    private <T> String logErrors(Set<ConstraintViolation<T>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
