package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;

import java.util.Collection;

public interface UserService {

    void save(UserDto user);

    User findById(int id);

    Collection<User> findByName(String name);

    Collection<User> findAll();

    Collection<Cat> findCatsByUserId(int id);

    void delete(int id);

    void assignCat(int userId, CatDto catDto);

    void update(int id, UserDto user);
}
