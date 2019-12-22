package com.leverx.servletapp.animal.repository;

import com.leverx.servletapp.animal.entity.Animal;

import java.util.Collection;

public interface AnimalRepository {

    Collection<Animal> findByOwnerId(int ownerId);

    Collection<Animal> findAll();
}
