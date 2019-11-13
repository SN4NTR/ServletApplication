package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public interface UserService {

    void save(User user);

    Collection<User> findByFirstName(String firstName);

    User findById(int id);

    Collection<User> findAll();

    void delete(int id);

    void update(User user);
}
