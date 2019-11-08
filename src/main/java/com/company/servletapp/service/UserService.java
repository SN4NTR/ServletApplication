package com.company.servletapp.service;

import com.company.servletapp.entity.User;

import java.util.List;

public interface UserService {

    void save(User user);

    List<User> getByFirstName(String firstName);

    User getById(int id);

    List<User> getAll();

    void delete(int id);

    void update(User user);
}
