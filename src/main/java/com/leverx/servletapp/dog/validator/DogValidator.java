package com.leverx.servletapp.dog.validator;

import com.leverx.servletapp.dog.repository.DogRepository;
import com.leverx.servletapp.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.leverx.servletapp.constant.HttpResponseStatus.NOT_FOUND;
import static com.leverx.servletapp.constant.HttpResponseStatus.UNPROCESSABLE_ENTITY;
import static com.leverx.servletapp.message.MessageConstant.DOG_NOT_FOUND;
import static com.leverx.servletapp.message.MessageConstant.getLocalizedMessage;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class DogValidator {

    public static final int MIN_VALUE = 0;

    private DogRepository dogRepository;

    public void validateId(int id) throws EntityNotFoundException {
        var dogOpt = dogRepository.findById(id);
        var message = getLocalizedMessage(DOG_NOT_FOUND);
        dogOpt.orElseThrow(() -> new EntityNotFoundException(message, NOT_FOUND));
    }

    public void validateIds(List<Integer> ids) throws EntityNotFoundException {
        if (isNotEmpty(ids)) {
            for (var id : ids) {
                var dogOpt = dogRepository.findById(id);
                var message = getLocalizedMessage(DOG_NOT_FOUND);
                dogOpt.orElseThrow(() -> new EntityNotFoundException(message, UNPROCESSABLE_ENTITY));
            }
        }
    }
}
