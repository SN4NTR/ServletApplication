package com.leverx.servletapp.model.animal.parent.repository;

import com.leverx.servletapp.model.animal.parent.Animal;

import java.util.Collection;

public interface AnimalRepository {

    Collection<Animal> findByOwnerId(int ownerId);
}
