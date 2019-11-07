package com.company.servletapp.service;

import com.company.servletapp.entity.User;
import com.company.servletapp.repository.UserRepository;
import com.company.servletapp.repository.UserRepositoryImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
