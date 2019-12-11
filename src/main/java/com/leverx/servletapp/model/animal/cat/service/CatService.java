package com.leverx.servletapp.model.animal.cat.service;

import com.leverx.servletapp.model.animal.cat.dto.CatInputDto;
import com.leverx.servletapp.model.animal.cat.dto.CatOutputDto;
import com.leverx.servletapp.model.animal.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;

import java.util.Collection;

public interface CatService {

    void save(CatInputDto catInputDto) throws ValidationException;

    CatWithOwnerDto findById(int id) throws EntityNotFoundException;

    Collection<CatOutputDto> findAll();
}
