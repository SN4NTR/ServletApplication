package com.leverx.servletapp.animal.service;

import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import com.leverx.servletapp.exception.EntityNotFoundException;

import java.util.Collection;

public interface AnimalService {

    Collection<AnimalOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException;

    Collection<AnimalOutputDto> findAll();
}
