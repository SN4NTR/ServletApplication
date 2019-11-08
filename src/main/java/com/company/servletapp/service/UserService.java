package com.company.servletapp.service;

import java.io.BufferedReader;
import java.util.List;

public interface UserService {

    void save(BufferedReader reader);

    List<String> getByFirstName(String firstName);

    String getById(String id);

    List<String> getAll();

    void delete(String id);

    void update(BufferedReader reader, String id);
}
