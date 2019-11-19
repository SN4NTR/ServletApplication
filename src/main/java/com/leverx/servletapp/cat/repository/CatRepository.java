package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public interface CatRepository {

    void save(Cat cat);

    Cat findById(int id);

    Cat findByOwner(User owner);

    Collection<Cat> findAll();
}
