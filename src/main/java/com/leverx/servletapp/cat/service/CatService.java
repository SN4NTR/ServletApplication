package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.dto.CatInputDto;
import com.leverx.servletapp.cat.entity.dto.CatOutputDto;
import com.leverx.servletapp.cat.entity.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.entity.dto.CatWithOwnerDto;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.InputDataException;

import java.util.Collection;
import java.util.Optional;

public interface CatService {

    void save(CatInputDto catInputDto) throws InputDataException;

    void assignCat(int userId, CatWithIdsDto catWithIdsDto) throws EntityNotFoundException;

    Optional<CatWithOwnerDto> findById(int id) throws EntityNotFoundException;

    Collection<CatOutputDto> findAll() throws EntityNotFoundException;

    Collection<CatOutputDto> findByOwnerId(int id) throws EntityNotFoundException;
}
