package com.leverx.servletapp.model.animal.dog.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.animal.dog.dto.DogInputDto;
import com.leverx.servletapp.model.animal.dog.dto.DogOutputDto;
import com.leverx.servletapp.model.animal.dog.dto.DogWithOwnerDto;

import java.util.Collection;

public interface DogService {

    void save(DogInputDto dogInputDto) throws ValidationException;

    DogWithOwnerDto findById(int id) throws EntityNotFoundException;

    Collection<DogOutputDto> findAll();
}
