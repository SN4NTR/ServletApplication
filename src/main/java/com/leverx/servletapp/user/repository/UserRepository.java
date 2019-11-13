package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    List<User> getByFirstName(String firstName);

    User getById(int id);

    List<User> getAll();

    void delete(int id);

    void update(User user);
}
