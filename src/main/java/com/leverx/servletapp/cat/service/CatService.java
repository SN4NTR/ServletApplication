package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.user.entity.User;

import java.util.Collection;

public interface CatService {

    void save(CatDto cat);

    Cat findById(int id);

    Cat findByOwner(User owner);

    Collection<Cat> findAll();
}
