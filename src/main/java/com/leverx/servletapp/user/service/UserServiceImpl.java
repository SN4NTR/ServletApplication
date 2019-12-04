package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.dto.UserInputDto;
import com.leverx.servletapp.user.entity.dto.UserOutputDto;
import com.leverx.servletapp.user.entity.dto.UserWithCatsDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.NoSuchElementException;

import static com.leverx.servletapp.user.mapper.UserMapper.collectionToOutputDtoList;
import static com.leverx.servletapp.user.mapper.UserMapper.fromInputDto;
import static com.leverx.servletapp.user.mapper.UserMapper.toWithCatsDto;
import static com.leverx.servletapp.validator.EntityValidator.validateEntity;

@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(UserInputDto userInputDto) throws IllegalArgumentException {
        validateEntity(userInputDto);
        var user = fromInputDto(userInputDto);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws IllegalArgumentException, NoSuchElementException {
        validateEntity(userInputDto);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow();
        var firstName = userInputDto.getFirstName();
        user.setFirstName(firstName);
        userRepository.update(user);
    }

    @Override
    public UserWithCatsDto findById(int id) throws NoSuchElementException {
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow();
        return toWithCatsDto(user);
    }

    @Override
    public Collection<UserOutputDto> findByName(String name) throws NoSuchElementException {
        var users = userRepository.findByName(name);
        return collectionToOutputDtoList(users);
    }

    @Override
    public Collection<UserOutputDto> findAll() throws NoSuchElementException {
        var users = userRepository.findAll();
        return collectionToOutputDtoList(users);
    }
}
