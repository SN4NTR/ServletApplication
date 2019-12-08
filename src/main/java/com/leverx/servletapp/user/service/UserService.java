package com.leverx.servletapp.user.service;

import com.leverx.servletapp.exception.InputDataException;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithCatsDto;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    void save(UserInputDto user) throws InputDataException;

    void delete(int id);

    void update(int id, UserInputDto user) throws InputDataException, EntityNotFoundException;

    Optional<UserWithCatsDto> findById(int id) throws EntityNotFoundException;

    Collection<UserOutputDto> findByName(String name) throws EntityNotFoundException;

    Collection<UserOutputDto> findAll() throws EntityNotFoundException;
}
