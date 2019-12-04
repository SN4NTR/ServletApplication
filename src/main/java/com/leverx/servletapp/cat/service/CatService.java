package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.dto.CatInputDto;
import com.leverx.servletapp.cat.entity.dto.CatOutputDto;
import com.leverx.servletapp.cat.entity.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.entity.dto.CatWithOwnerDto;

import java.util.Collection;

public interface CatService {

    void save(CatInputDto catInputDto);

    void assignCat(int userId, CatWithIdsDto catWithIdsDto);

    CatWithOwnerDto findById(int id);

    Collection<CatOutputDto> findAll();

    Collection<CatOutputDto> findByOwnerId(int id);
}
