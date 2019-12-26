package com.leverx.servletapp.animal.service;

import com.leverx.servletapp.animal.converter.AnimalConverter;
import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import com.leverx.servletapp.animal.dto.AnimalWithOwnerDto;
import com.leverx.servletapp.animal.entity.Animal;
import com.leverx.servletapp.animal.repository.AnimalRepository;
import com.leverx.servletapp.animal.validator.AnimalValidator;
import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;

import java.util.Collection;

import static com.leverx.servletapp.animal.converter.AnimalConverter.toDtoWithOwner;

@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private AnimalRepository animalRepository;
    private AnimalValidator animalValidator;
    private UserValidator userValidator;

    @Override
    public AnimalWithOwnerDto findById(int id) throws EntityNotFoundException {
        animalValidator.validateId(id);
        var animalOpt = animalRepository.findById(id, Animal.class);
        var animal = animalOpt.orElseThrow();
        return toDtoWithOwner(animal);
    }

    @Override
    public Collection<AnimalOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException {
        userValidator.validateId(ownerId);
        var animals = animalRepository.findByOwnerId(ownerId, Animal.class);
        return AnimalConverter.toOutputDtoList(animals);
    }

    @Override
    public Collection<AnimalOutputDto> findAll() {
        var animals = animalRepository.findAll(Animal.class);
        return AnimalConverter.toOutputDtoList(animals);
    }
}
