package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;

import java.util.Collection;

public interface UserService {

    void save(UserDto user);

    User findById(int id);

    Collection<User> findAll();

    void delete(int id);

    void update(int id, UserDto user);
}
