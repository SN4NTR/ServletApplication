package com.leverx.servletapp.user.service;

import com.leverx.servletapp.exception.InputDataException;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithCatsDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.user.mapper.UserMapper.collectionToOutputDtoList;
import static com.leverx.servletapp.user.mapper.UserMapper.fromInputDto;
import static com.leverx.servletapp.user.mapper.UserMapper.toWithCatsDto;
import static com.leverx.servletapp.validator.EntityValidator.validateEntity;

@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(UserInputDto userInputDto) throws InputDataException {
        validateEntity(userInputDto);
        var user = fromInputDto(userInputDto);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, InputDataException {
        validateEntity(userInputDto);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow();
        var firstName = userInputDto.getFirstName();
        user.setFirstName(firstName);
        userRepository.update(user);
    }

    @Override
    public Optional<UserWithCatsDto> findById(int id) throws EntityNotFoundException {
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow();
        var userWithCatsDto = toWithCatsDto(user);
        return Optional.of(userWithCatsDto);
    }

    @Override
    public Collection<UserOutputDto> findByName(String name) throws EntityNotFoundException {
        var users = userRepository.findByName(name);
        return collectionToOutputDtoList(users);
    }

    @Override
    public Collection<UserOutputDto> findAll() throws EntityNotFoundException {
        var users = userRepository.findAll();
        return collectionToOutputDtoList(users);
    }
}
