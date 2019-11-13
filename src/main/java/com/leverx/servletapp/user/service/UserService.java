package com.leverx.servletapp.user.service;

import java.io.BufferedReader;
import java.util.Collection;

public interface UserService {

    void save(BufferedReader reader);

    Collection<String> findByFirstName(String firstName);

    String findById(String id);

    Collection<String> findAll();

    void delete(String id);

    void update(BufferedReader reader, String id);
}
