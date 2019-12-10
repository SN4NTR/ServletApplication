package com.leverx.servletapp.user.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithCatsDto;

import java.util.Collection;

public interface UserService {

    void save(UserInputDto user) throws ValidationException, EntityNotFoundException;

    void delete(int id);

    void update(int id, UserInputDto user) throws ValidationException, EntityNotFoundException;

    UserWithCatsDto findById(int id) throws EntityNotFoundException;

    Collection<UserOutputDto> findByName(String name);

    Collection<UserOutputDto> findAll();
}
