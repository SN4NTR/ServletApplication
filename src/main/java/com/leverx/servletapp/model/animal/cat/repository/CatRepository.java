package com.leverx.servletapp.model.animal.cat.repository;

import com.leverx.servletapp.model.animal.cat.entity.Cat;

import java.util.Collection;
import java.util.Optional;

public interface CatRepository {

    void save(Cat cat);

    Optional<Cat> findById(int id);

    Collection<Cat> findByOwnerId(int id);

    Collection<Cat> findAll();
}
