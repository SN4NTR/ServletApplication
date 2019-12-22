package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;

import java.util.Collection;
import java.util.Optional;

public interface CatRepository {

    void save(Cat cat);

    Optional<Cat> findById(int id);

    Collection<Cat> findByOwnerId(int ownerId);

    Collection<Cat> findAll();
}
