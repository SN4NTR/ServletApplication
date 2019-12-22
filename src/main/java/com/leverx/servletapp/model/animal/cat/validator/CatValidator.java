package com.leverx.servletapp.model.animal.cat.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.model.animal.cat.repository.CatRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.context.ApplicationContext.getBean;
import static com.leverx.servletapp.exception.constant.BundleConstant.CAT_NOT_FOUND;
import static com.leverx.servletapp.exception.constant.BundleConstant.MESSAGE_BUNDLE_NAME;
import static com.leverx.servletapp.exception.constant.BundleConstant.getLocalizedMessage;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class CatValidator {

    public static final int MIN_VALUE = 0;

    private static final CatRepository CAT_REPOSITORY;

    static {
        CAT_REPOSITORY = getBean(CatRepository.class);
    }

    public static void validateId(int id) throws EntityNotFoundException {
        var catOpt = CAT_REPOSITORY.findById(id);
        var message = getLocalizedMessage(MESSAGE_BUNDLE_NAME, CAT_NOT_FOUND);
        catOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public static void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var catOpt = CAT_REPOSITORY.findById(id);
                var message = getLocalizedMessage(MESSAGE_BUNDLE_NAME, CAT_NOT_FOUND);
                catOpt.orElseThrow(() -> new EntityNotFoundException(message, UNPROCESSABLE_ENTITY));
            }
        }
    }
}
