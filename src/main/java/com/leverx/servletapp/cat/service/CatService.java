package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;

import java.util.Collection;

public interface CatService {

    void save(CatDto catDto);

    Cat findById(int id);

    Collection<Cat> findAll();
}
