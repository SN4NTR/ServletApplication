package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.dto.CatOutputDto;
import com.leverx.servletapp.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;

import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.cat.converter.CatConverter.fromInputDto;
import static com.leverx.servletapp.cat.converter.CatConverter.toDtoWithOwner;
import static com.leverx.servletapp.cat.converter.CatConverter.toOutputDtoList;
import static com.leverx.servletapp.validator.EntityValidator.validateEntity;

public class CatServiceImpl implements CatService {

    private CatRepository catRepository = new CatRepositoryImpl();

    @Override
    public void save(CatInputDto catInputDto) throws ValidationException {
        validateEntity(catInputDto);
        var cat = fromInputDto(catInputDto);
        catRepository.save(cat);
    }

    @Override
    public Optional<CatWithOwnerDto> findById(int id) throws EntityNotFoundException {
        var catOtp = catRepository.findById(id);
        var cat = catOtp.orElseThrow();
        var catWithOwnerDto = toDtoWithOwner(cat);
        return Optional.of(catWithOwnerDto);
    }

    @Override
    public Collection<CatOutputDto> findAll() throws EntityNotFoundException {
        var cats = catRepository.findAll();
        return toOutputDtoList(cats);
    }

    @Override
    public Collection<CatOutputDto> findByOwnerId(int id) throws EntityNotFoundException {
        var cats = catRepository.findByOwnerId(id);
        return toOutputDtoList(cats);
    }
}
