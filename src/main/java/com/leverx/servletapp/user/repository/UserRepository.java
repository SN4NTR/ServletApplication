package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public interface UserRepository {

    void save(User user);

    User findById(int id);

    Collection<User> findByName(String name);

    Collection<User> findAll();

    void delete(int id);

    void update(int id, User user);
}
