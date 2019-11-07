package com.company.servletapp.repository;

import com.company.servletapp.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User getById(int id);

    List<User> getAll();
}
