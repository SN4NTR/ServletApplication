package com.leverx.servletapp.animal.repository;

import com.leverx.servletapp.animal.entity.Animal;

import java.util.Collection;
import java.util.Optional;

public interface AnimalRepository {

    <T extends Animal> Optional<T> findById(int id, Class<T> tClass);

    <T extends Animal> Collection<T> findByOwnerId(int ownerId, Class<T> tClass);

    <T extends Animal> Collection<T> findAll(Class<T> tClass);
}
