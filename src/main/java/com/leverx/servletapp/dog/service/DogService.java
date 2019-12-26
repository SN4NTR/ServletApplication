package com.leverx.servletapp.dog.service;

import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.dog.dto.DogInputDto;
import com.leverx.servletapp.dog.dto.DogOutputDto;
import com.leverx.servletapp.dog.dto.DogWithOwnerDto;

import java.util.Collection;

public interface DogService {

    void save(DogInputDto dogInputDto) throws ValidationException;

    DogWithOwnerDto findById(int id) throws EntityNotFoundException;

    Collection<DogOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException;

    Collection<DogOutputDto> findAll();
}
