package com.leverx.servletapp.dog.service;

import com.leverx.servletapp.dog.dto.DogInputDto;
import com.leverx.servletapp.dog.dto.DogOutputDto;
import com.leverx.servletapp.dog.dto.DogWithOwnerDto;
import com.leverx.servletapp.dog.repository.DogRepository;
import com.leverx.servletapp.dog.validator.DogValidator;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;

import java.util.Collection;

import static com.leverx.servletapp.animal.validator.AnimalValidator.validateInputDto;
import static com.leverx.servletapp.dog.converter.DogConverter.fromInputDto;
import static com.leverx.servletapp.dog.converter.DogConverter.toDtoWithOwner;
import static com.leverx.servletapp.dog.converter.DogConverter.toOutputDtoList;

@AllArgsConstructor
public class DogServiceImpl implements DogService {

    private DogRepository dogRepository;

    @Override
    public void save(DogInputDto dogInputDto) throws ValidationException {
        validateInputDto(dogInputDto);
        var dog = fromInputDto(dogInputDto);
        dogRepository.save(dog);
    }

    @Override
    public DogWithOwnerDto findById(int id) throws EntityNotFoundException {
        var dogValidator = new DogValidator(dogRepository);
        dogValidator.validateId(id);
        var dogDto = dogRepository.findById(id);
        var dog = dogDto.orElseThrow();
        return toDtoWithOwner(dog);
    }

    @Override
    public Collection<DogOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException {
        var userValidator = new UserValidator(new UserRepositoryImpl());
        userValidator.validateId(ownerId);
        var dogs = dogRepository.findByOwnerId(ownerId);
        return toOutputDtoList(dogs);
    }

    @Override
    public Collection<DogOutputDto> findAll() {
        var dogs = dogRepository.findAll();
        return toOutputDtoList(dogs);
    }
}
