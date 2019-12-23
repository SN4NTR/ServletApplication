package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.animal.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.cat.entity.Cat;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class CatRepositoryImpl extends AnimalRepositoryImpl implements CatRepository {

    @Override
    public void save(Cat cat) {
        log.info("Saving cat with name '{}'.", cat.getName());
        super.save(cat);
    }

    @Override
    public Optional<Cat> findById(int id) {
        log.info("Getting cat with id = {}", id);
        return super.findById(id, Cat.class);
    }

    @Override
    public Collection<Cat> findByOwnerId(int ownerId) {
        log.info("Getting cats by owner id");
        return super.findByOwnerId(ownerId, Cat.class);
    }

    @Override
    public Collection<Cat> findAll() {
        log.info("Getting all cats");
        return super.findAll(Cat.class);
    }
}
