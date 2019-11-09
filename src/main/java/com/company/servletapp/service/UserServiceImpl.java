package com.company.servletapp.service;

import com.company.servletapp.entity.User;
import com.company.servletapp.repository.UserRepository;
import com.company.servletapp.repository.UserRepositoryImpl;
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
    public List<String> getByFirstName(String firstName) {
        return userRepository.getByFirstName(firstName)
                .stream()
                .map(gson::toJson)
                .collect(Collectors.toList());
    }

    @Override
    public String getById(String id) {
        User user = userRepository.getById(Integer.parseInt(id));

        return gson.toJson(user);
    }

    @Override
    public List<String> getAll() {
        return userRepository.getAll()
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
