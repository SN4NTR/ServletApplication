package com.leverx.servletapp.animal.service;

import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import com.leverx.servletapp.animal.dto.AnimalWithOwnerDto;
import com.leverx.servletapp.core.exception.EntityNotFoundException;

import java.util.Collection;

public interface AnimalService {

    AnimalWithOwnerDto findById(int id) throws EntityNotFoundException;

    Collection<AnimalOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException;

    Collection<AnimalOutputDto> findAll();
}
