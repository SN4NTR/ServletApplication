package com.leverx.servletapp.cat.validator;

import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.core.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.leverx.servletapp.web.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.web.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.core.exception.ErrorConstant.CAT_NOT_FOUND;
import static com.leverx.servletapp.core.exception.ErrorConstant.getLocalizedMessage;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class CatValidator {

    public static final int MIN_VALUE = 0;

    private CatRepository catRepository;

    public void validateId(int id) throws EntityNotFoundException {
        var catOpt = catRepository.findById(id);
        var message = getLocalizedMessage(CAT_NOT_FOUND);
        catOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var catOpt = catRepository.findById(id);
                var message = getLocalizedMessage(CAT_NOT_FOUND);
                catOpt.orElseThrow(() -> new EntityNotFoundException(message, UNPROCESSABLE_ENTITY));
            }
        }
    }
}
