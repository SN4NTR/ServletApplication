package com.leverx.servletapp.dog.repository;

import com.leverx.servletapp.dog.entity.Dog;

import java.util.Collection;
import java.util.Optional;

public interface DogRepository {

    void save(Dog dog);

    Optional<Dog> findById(int id);

    Collection<Dog> findByOwnerId(int ownerId);

    Collection<Dog> findAll();
}
