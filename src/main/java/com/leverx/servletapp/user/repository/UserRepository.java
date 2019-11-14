package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;

import java.util.Collection;

public interface UserRepository {

    void save(UserDto user);

    User findById(int id);

    Collection<User> findAll();

    void delete(int id);

    void update(int id, UserDto user);
}
