package com.leverx.servletapp.model.animal.parent.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.model.animal.parent.dto.AnimalOutputDto;

import java.util.Collection;

public interface AnimalService {

    Collection<AnimalOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException;

    Collection<AnimalOutputDto> findAll();
}
