package com.leverx.servletapp.model.user.service;

import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.animal.parent.dto.AnimalOutputDto;
import com.leverx.servletapp.model.user.dto.UserInputDto;
import com.leverx.servletapp.model.user.dto.UserOutputDto;
import com.leverx.servletapp.model.user.dto.UserWithAnimalsDto;

import java.util.Collection;

public interface UserService {

    void save(UserInputDto user) throws ValidationException, EntityNotFoundException;

    void delete(int id) throws EntityNotFoundException;

    void update(int id, UserInputDto user) throws ValidationException, EntityNotFoundException;

    UserWithAnimalsDto findById(int id) throws EntityNotFoundException;

    Collection<UserOutputDto> findByName(String name);

    Collection<UserOutputDto> findAll();
}
