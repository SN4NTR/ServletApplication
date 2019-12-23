package com.leverx.servletapp.user.service;

import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.TransferException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithAnimalsDto;

import java.util.Collection;

public interface UserService {

    void save(UserInputDto user) throws ValidationException, EntityNotFoundException;

    void delete(int id) throws EntityNotFoundException;

    void update(int id, UserInputDto user) throws ValidationException, EntityNotFoundException;

    void transferAnimalPoints(int senderId, int receiverId, int animalPoints) throws EntityNotFoundException, TransferException;

    UserWithAnimalsDto findById(int id) throws EntityNotFoundException;

    Collection<UserOutputDto> findByName(String name);

    Collection<UserOutputDto> findAll();
}
