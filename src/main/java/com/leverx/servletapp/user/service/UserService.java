package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.dto.UserInputDto;
import com.leverx.servletapp.user.entity.dto.UserOutputDto;
import com.leverx.servletapp.user.entity.dto.UserWithCatsDto;

import java.util.Collection;

public interface UserService {

    void save(UserInputDto user);

    void delete(int id);

    void update(int id, UserInputDto user);

    UserWithCatsDto findById(int id);

    Collection<UserOutputDto> findByName(String name);

    Collection<UserOutputDto> findAll();
}
