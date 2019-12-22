package com.leverx.servletapp.animal.service;

import com.leverx.servletapp.animal.converter.AnimalConverter;
import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import com.leverx.servletapp.animal.repository.AnimalRepository;
import com.leverx.servletapp.annotation.Service;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private AnimalRepository animalRepository;

    @Override
    public Collection<AnimalOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException {
        var userValidator = new UserValidator(new UserRepositoryImpl());
        userValidator.validateId(ownerId);
        var animals = animalRepository.findByOwnerId(ownerId);
        return AnimalConverter.toOutputDtoList(animals);
    }

    @Override
    public Collection<AnimalOutputDto> findAll() {
        var animals = animalRepository.findAll();
        return AnimalConverter.toOutputDtoList(animals);
    }
}
