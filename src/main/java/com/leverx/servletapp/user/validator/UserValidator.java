package com.leverx.servletapp.user.validator;

import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.cat.validator.CatValidator;
import com.leverx.servletapp.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.dog.validator.DogValidator;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.message.MessageConstant.USER_NOT_FOUND;
import static com.leverx.servletapp.message.MessageConstant.getLocalizedMessage;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class UserValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;

    private static final String DELIMITER = "; ";

    private UserRepository userRepository;

    public void validateId(int id) throws EntityNotFoundException {
        var userOpt = userRepository.findById(id);
        var message = getLocalizedMessage(USER_NOT_FOUND);
        userOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public static void validateInputDto(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(userInputDto);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }

        var catsIds = userInputDto.getCatsIds();
        var catValidator = new CatValidator(new CatRepositoryImpl());
        catValidator.validateIds(catsIds);
        var dogsIds = userInputDto.getDogsIds();
        var dogValidator = new DogValidator(new DogRepositoryImpl());
        dogValidator.validateIds(dogsIds);
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
