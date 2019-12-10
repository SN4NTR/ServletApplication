package com.leverx.servletapp.model.animal.dog.repository;

import com.leverx.servletapp.model.animal.dog.entity.Dog;

import java.util.Collection;
import java.util.Optional;

public interface DogRepository {

    void save(Dog dog);

    Optional<Dog> findById(int id);

//    Collection<Dog> findByOwnerId(int id);

    Collection<Dog> findAll();
}
