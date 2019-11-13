package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.entity.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final static Gson gson = new Gson();

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(BufferedReader reader) {
        User user = gson.fromJson(reader, User.class);
        userRepository.save(user);
    }

    @Override
    public List<String> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName)
                .stream()
                .map(gson::toJson)
                .collect(Collectors.toList());
    }

    @Override
    public String findById(String id) {
        User user = userRepository.findById(Integer.parseInt(id));

        return gson.toJson(user);
    }

    @Override
    public List<String> findAll() {
        return userRepository.findAll()
                .stream()
                .map(gson::toJson)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        userRepository.delete(Integer.parseInt(id));
    }

    @Override
    public void update(BufferedReader reader, String id) {
        User user = gson.fromJson(reader, User.class);
        user.setId(Integer.parseInt(id));
        userRepository.update(user);
    }
}
