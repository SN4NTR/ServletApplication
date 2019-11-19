package com.leverx.servletapp.cat.repository;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public class CatRepositoryImpl implements CatRepository {

    @Override
    public void save(Cat cat) {

    }

    @Override
    public Cat findById(int id) {
        return null;
    }

    @Override
    public Cat findByOwner(User owner) {
        return null;
    }

    @Override
    public Collection<Cat> findAll() {
        return null;
    }
}
