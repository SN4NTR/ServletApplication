package com.leverx.servletapp.cat.validator;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.Collections.emptyList;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class CatValidator {

    public static final int NAME_MIN_SIZE = 5;
    public static final int NAME_MAX_SIZE = 60;
    private static final String DELIMITER = "; ";

    private static final String CAT_DOES_NOT_EXIST = "Cat doesn't exist";

    private static final CatRepository CAT_REPOSITORY = new CatRepositoryImpl();

    public static void validateInputDto(CatInputDto catInputDto) throws ValidationException {
        var validatorFactory = buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var violations = validator.validate(catInputDto);
        if (isNotEmpty(violations)) {
            var message = "Cat: " + logErrors(violations);
            throw new ValidationException(message);
        }
    }

    public static List<Cat> ifValidIdsGetList(List<Integer> ids) throws ValidationException {
        if (isNotEmpty(ids)) {
            var cats = new ArrayList<Cat>();

            for (var id : ids) {
                var catOpt = CAT_REPOSITORY.findById(id);
                var cat = catOpt.orElseThrow(() -> new ValidationException(CAT_DOES_NOT_EXIST));
                cats.add(cat);
            }
            return cats;
        }
        return emptyList();
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
