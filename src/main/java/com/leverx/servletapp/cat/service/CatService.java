package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.dto.CatOutputDto;
import com.leverx.servletapp.cat.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.InputDataException;

import java.util.Collection;
import java.util.Optional;

public interface CatService {

    void save(CatInputDto catInputDto) throws InputDataException;

    void assignToUser(int userId, CatWithIdsDto catWithIdsDto) throws EntityNotFoundException;

    void transfer(int userIdFrom, int userIdTo, Collection<Integer> catIds) throws EntityNotFoundException;

    Optional<CatWithOwnerDto> findById(int id) throws EntityNotFoundException;

    Collection<CatOutputDto> findAll() throws EntityNotFoundException;

    Collection<CatOutputDto> findByOwnerId(int id) throws EntityNotFoundException;
}
