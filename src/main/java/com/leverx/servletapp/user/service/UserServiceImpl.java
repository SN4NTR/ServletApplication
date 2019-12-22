package com.leverx.servletapp.user.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.converter.UserConverter;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static com.leverx.servletapp.user.converter.UserConverter.fromInputDto;
import static com.leverx.servletapp.user.validator.UserValidator.validateInputDto;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        validateInputDto(userInputDto);
        var user = fromInputDto(userInputDto);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        validateInputDto(userInputDto);

        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        var userFromDto = fromInputDto(userInputDto);
        var existingAnimals = user.getAnimals();
        var newAnimals = userFromDto.getAnimals();
        existingAnimals.addAll(newAnimals);

        user.setId(id);
        userRepository.update(user);
    }

//    @Override
//    public void transferAnimalPoint() {
//
//    }

    @Override
    public UserWithAnimalsDto findById(int id) throws EntityNotFoundException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        return UserConverter.toWithAnimalsDto(user);
    }

    @Override
    public Collection<UserOutputDto> findByName(String name) {
        var users = userRepository.findByName(name);
        return UserConverter.toOutputDtoList(users);
    }

    @Override
    public Collection<UserOutputDto> findAll() {
        var users = userRepository.findAll();
        return UserConverter.toOutputDtoList(users);
    }
}
