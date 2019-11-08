package com.company.servletapp.service;

import com.company.servletapp.entity.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User getByFirstName(String firstName);

    List<User> getAll();
}
