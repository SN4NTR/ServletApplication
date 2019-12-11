package com.leverx.servletapp.model.user.repository;

import com.leverx.servletapp.model.animal.parent.Animal;
import com.leverx.servletapp.model.user.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    void delete(int id);

    void update(User user);

    Optional<User> findById(int id);

    Collection<Animal> findAnimals(int id);

    Collection<User> findByName(String name);

    Collection<User> findAll();
}
