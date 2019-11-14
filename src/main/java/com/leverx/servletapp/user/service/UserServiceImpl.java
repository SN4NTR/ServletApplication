package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;

import java.util.Collection;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(UserDto user) {
        userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Collection<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserDto user) {
        userRepository.update(id, user);
    }
}
