package com.leverx.servletapp.model.animal.dog.validator;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.model.animal.dog.repository.DogRepository;
import com.leverx.servletapp.model.animal.dog.repository.DogRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.context.ApplicationContext.getBean;
import static com.leverx.servletapp.exception.constant.BundleConstant.DOG_NOT_FOUND;
import static com.leverx.servletapp.exception.constant.BundleConstant.getLocalizedMessage;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class DogValidator {

    public static final int MIN_VALUE = 0;

    private static DogRepository dogRepository;

    static {
        dogRepository = (DogRepositoryImpl) getBean(DogRepository.class);
    }

    public static void validateId(int id) throws EntityNotFoundException {
        var dogOpt = dogRepository.findById(id);
        var message = getLocalizedMessage(DOG_NOT_FOUND);
        dogOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public static void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var dogOpt = dogRepository.findById(id);
                var message = getLocalizedMessage(DOG_NOT_FOUND);
                dogOpt.orElseThrow(() -> new EntityNotFoundException(message, UNPROCESSABLE_ENTITY));
            }
        }
    }
}
