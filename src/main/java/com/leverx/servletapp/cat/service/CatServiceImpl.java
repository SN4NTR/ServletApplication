package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public class CatServiceImpl implements CatService {

    @Override
    public void save(CatDto cat) {

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
