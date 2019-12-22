package com.leverx.servletapp.cat.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.cat.repository.CatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.message.MessageConstant.CAT_NOT_FOUND;
import static com.leverx.servletapp.message.MessageConstant.MESSAGE_BUNDLE_NAME;
import static com.leverx.servletapp.message.MessageConstant.getLocalizedMessage;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class CatValidator {

    public static final int MIN_VALUE = 0;

    private CatRepository catRepository;

    public void validateId(int id) throws EntityNotFoundException {
        var catOpt = catRepository.findById(id);
        var message = getLocalizedMessage(MESSAGE_BUNDLE_NAME, CAT_NOT_FOUND);
        catOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var catOpt = catRepository.findById(id);
                var message = getLocalizedMessage(MESSAGE_BUNDLE_NAME, CAT_NOT_FOUND);
                catOpt.orElseThrow(() -> new EntityNotFoundException(message, UNPROCESSABLE_ENTITY));
            }
        }
    }
}
