package com.leverx.servletapp.model.animal.dog.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.animal.dog.dto.DogInputDto;
import com.leverx.servletapp.model.animal.dog.dto.DogOutputDto;
import com.leverx.servletapp.model.animal.dog.dto.DogWithOwnerDto;
import com.leverx.servletapp.model.animal.dog.repository.DogRepository;
import com.leverx.servletapp.model.animal.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.model.user.validator.UserValidator;

import java.util.Collection;

import static com.leverx.servletapp.model.animal.dog.converter.DogConverter.fromInputDto;
import static com.leverx.servletapp.model.animal.dog.converter.DogConverter.toDtoWithOwner;
import static com.leverx.servletapp.model.animal.dog.converter.DogConverter.toOutputDtoList;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.validateId;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.validateInputDto;

public class DogServiceImpl implements DogService {

    private DogRepository dogRepository = new DogRepositoryImpl();

    @Override
    public void save(DogInputDto dogInputDto) throws ValidationException {
        validateInputDto(dogInputDto);
        var dog = fromInputDto(dogInputDto);
        dogRepository.save(dog);
    }

    @Override
    public DogWithOwnerDto findById(int id) throws EntityNotFoundException {
        validateId(id);
        var dogDto = dogRepository.findById(id);
        var dog = dogDto.orElseThrow();
        return toDtoWithOwner(dog);
    }

    @Override
    public Collection<DogOutputDto> findAll() {
        var dogs = dogRepository.findAll();
        return toOutputDtoList(dogs);
    }

    @Override
    public Collection<DogOutputDto> findByOwnerId(int id) throws EntityNotFoundException {
        UserValidator.validateId(id);
        var dogs = dogRepository.findByOwnerId(id);
        return toOutputDtoList(dogs);
    }
}
