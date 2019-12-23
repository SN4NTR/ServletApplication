package com.leverx.servletapp.dog.repository;

import com.leverx.servletapp.animal.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.dog.entity.Dog;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class DogRepositoryImpl extends AnimalRepositoryImpl implements DogRepository {

    @Override
    public void save(Dog dog) {
        log.info("Saving dog with name '{}'.", dog.getName());
        super.save(dog);
    }

    @Override
    public Optional<Dog> findById(int id) {
        log.info("Getting dog with id = {}", id);
        return super.findById(id, Dog.class);
    }

    @Override
    public Collection<Dog> findByOwnerId(int ownerId) {
        log.info("Getting dogs by owner id");
        return super.findByOwnerId(ownerId, Dog.class);
    }

    @Override
    public Collection<Dog> findAll() {
        log.info("Getting all dogs");
        return super.findAll(Dog.class);
    }
}
