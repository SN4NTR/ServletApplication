package com.leverx.servletapp.model.animal.cat.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.model.animal.cat.repository.CatRepository;
import com.leverx.servletapp.model.animal.cat.repository.CatRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class CatValidator {

    private static final String CAT_DOES_NOT_EXIST = "Cat doesn't exist";
    private static final CatRepository CAT_REPOSITORY = new CatRepositoryImpl();

    public static final int MIN_VALUE = 0;
    public static final String WRONG_VALUE = "Value is less than " + MIN_VALUE;

    public static void validateId(int id) throws EntityNotFoundException {
        var catOpt = CAT_REPOSITORY.findById(id);
        catOpt.orElseThrow(() -> new EntityNotFoundException(CAT_DOES_NOT_EXIST, NOT_FOUND));
    }

    public static void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var catOpt = CAT_REPOSITORY.findById(id);
                catOpt.orElseThrow(() -> new EntityNotFoundException(CAT_DOES_NOT_EXIST, UNPROCESSABLE_ENTITY));
            }
        }
    }
}
