package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public interface UserRepository {

    void save(User user);

    Collection<User> findByFirstName(String firstName);

    User findById(int id);

    Collection<User> findAll();

    void delete(int id);

    void update(User user);
}
