package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    void delete(int id);

    void update(User user);

    Optional<User> findById(int id);

    Collection<User> findByName(String name);

    Collection<User> findAll();
}
