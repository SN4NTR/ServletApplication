package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface CatRepository {

    void save(Cat cat);

    Optional<Cat> findById(int id) throws EntityNotFoundException;

    Collection<Cat> findByOwnerId(int id) throws EntityNotFoundException;

    Collection<Cat> findAll() throws EntityNotFoundException;
}
