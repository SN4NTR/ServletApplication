package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public interface UserService {

    void save(User user);

    User findById(int id);

    Collection<User> findAll();

    void delete(int id);

    void update(User user);
}
