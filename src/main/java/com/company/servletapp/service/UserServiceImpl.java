package com.company.servletapp.service;

import com.company.servletapp.entity.User;
import com.company.servletapp.repository.UserRepository;
import com.company.servletapp.repository.UserRepositoryImpl;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final static Gson GSON = new Gson();

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(BufferedReader reader) {
        User user = GSON.fromJson(reader, User.class);
        userRepository.save(user);
    }

    @Override
    public List<String> getByFirstName(String firstName) {
        List<User> users = userRepository.getByFirstName(firstName);
        List<String> usersToJson = new ArrayList<>();

        for (User u : users) {
            usersToJson.add(GSON.toJson(u));
        }

        return usersToJson;
    }

    @Override
    public String getById(String id) {
        User user = userRepository.getById(Integer.parseInt(id));

        return GSON.toJson(user);
    }

    @Override
    public List<String> getAll() {
        List<User> users = userRepository.getAll();
        List<String> usersToJson = new ArrayList<>();

        for (User u : users) {
            usersToJson.add(GSON.toJson(u));
        }

        return usersToJson;
    }

    @Override
    public void delete(String id) {
        userRepository.delete(Integer.parseInt(id));
    }

    @Override
    public void update(BufferedReader reader, String id) {
        User user = GSON.fromJson(reader, User.class);
        user.setId(Integer.parseInt(id));
        userRepository.update(user);
    }
}
