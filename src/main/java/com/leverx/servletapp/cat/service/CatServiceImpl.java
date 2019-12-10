package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.dto.CatOutputDto;
import com.leverx.servletapp.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.validator.UserValidator;

import java.util.Collection;

import static com.leverx.servletapp.cat.converter.CatConverter.fromInputDto;
import static com.leverx.servletapp.cat.converter.CatConverter.toDtoWithOwner;
import static com.leverx.servletapp.cat.converter.CatConverter.toOutputDtoList;
import static com.leverx.servletapp.cat.validator.CatValidator.validateId;
import static com.leverx.servletapp.cat.validator.CatValidator.validateInputDto;

public class CatServiceImpl implements CatService {

    private CatRepository catRepository = new CatRepositoryImpl();

    @Override
    public void save(CatInputDto catInputDto) throws ValidationException {
        validateInputDto(catInputDto);
        var cat = fromInputDto(catInputDto);
        catRepository.save(cat);
    }

    @Override
    public CatWithOwnerDto findById(int id) throws EntityNotFoundException {
        validateId(id);
        var catOtp = catRepository.findById(id);
        var cat = catOtp.orElseThrow(EntityNotFoundException::new);
        return toDtoWithOwner(cat);
    }

    @Override
    public Collection<CatOutputDto> findAll() {
        var cats = catRepository.findAll();
        return toOutputDtoList(cats);
    }

    @Override
    public Collection<CatOutputDto> findByOwnerId(int id) throws EntityNotFoundException {
        UserValidator.validateId(id);
        var cats = catRepository.findByOwnerId(id);
        return toOutputDtoList(cats);
    }
}
