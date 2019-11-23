package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;

import java.util.Collection;

public interface CatRepository {

    void save(Cat cat);

    Cat findById(int id);

    Collection<Cat> findAll();
}
