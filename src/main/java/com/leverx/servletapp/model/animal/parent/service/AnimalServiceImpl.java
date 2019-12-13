package com.leverx.servletapp.model.animal.parent.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.model.animal.parent.dto.AnimalOutputDto;
import com.leverx.servletapp.model.animal.parent.repository.AnimalRepository;
import com.leverx.servletapp.model.animal.parent.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.model.user.validator.UserValidator;

import java.util.Collection;

import static com.leverx.servletapp.model.animal.parent.converter.AnimalConverter.toOutputDtoList;

public class AnimalServiceImpl implements AnimalService {

    private AnimalRepository animalRepository = new AnimalRepositoryImpl();

    @Override
    public Collection<AnimalOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException {
        UserValidator.validateId(ownerId);
        var animals = animalRepository.findByOwnerId(ownerId);
        return toOutputDtoList(animals);
    }

    @Override
    public Collection<AnimalOutputDto> findAll() {
        var animals = animalRepository.findAll();
        return toOutputDtoList(animals);
    }
}
