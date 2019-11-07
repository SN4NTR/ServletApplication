package com.company.servletapp.service;

import com.company.servletapp.entity.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User getById(int id);

    List<User> getAll();
}
