package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.CatDto;

import java.util.Collection;

public interface CatService {

    void save(CatDto catDto);

    CatDto findById(int id);

    Collection<CatDto> findAll();
}
