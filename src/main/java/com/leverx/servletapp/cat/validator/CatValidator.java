package com.leverx.servletapp.cat.validator;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class CatValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;
    public static final String DELIMITER = "; ";
    private static final String CAT_DOES_NOT_EXIST = "Cat doesn't exist";
    public static final String WRONG_NAME_SIZE_MSG = "First name must be between " + NAME_MIN_SIZE + " and " + NAME_MAX_SIZE;
    public static final String WRONG_DATE_MSG = "Date must be past or present";

    private static final CatRepository CAT_REPOSITORY = new CatRepositoryImpl();

    public static void validateInputDto(CatInputDto catInputDto) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(catInputDto);
        if (isNotEmpty(violations)) {
            var message = logErrors(violations);
            throw new ValidationException(message, UNPROCESSABLE_ENTITY);
        }
    }

    public static void validateId(int id) throws EntityNotFoundException {
        var catOpt = CAT_REPOSITORY.findById(id);
        catOpt.orElseThrow(() -> new EntityNotFoundException(CAT_DOES_NOT_EXIST, UNPROCESSABLE_ENTITY));
    }

    public static void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var catOpt = CAT_REPOSITORY.findById(id);
                catOpt.orElseThrow(() -> new EntityNotFoundException(CAT_DOES_NOT_EXIST, UNPROCESSABLE_ENTITY));
            }
        }
    }

    private static String logErrors(Set<ConstraintViolation<CatInputDto>> violations) {
        var joiner = new StringJoiner(DELIMITER);
        violations.stream()
                .map(ConstraintViolation::getMessage)
                .peek(log::error)
                .forEach(joiner::add);

        return joiner.toString();
    }
}
