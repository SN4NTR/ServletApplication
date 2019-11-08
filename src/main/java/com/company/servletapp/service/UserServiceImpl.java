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
    public List<User> getByFirstName(String firstName) {
        return userRepository.getByFirstName(firstName);
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }
}
