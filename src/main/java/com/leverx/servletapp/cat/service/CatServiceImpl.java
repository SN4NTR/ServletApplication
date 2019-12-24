package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.dto.CatOutputDto;
import com.leverx.servletapp.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.validator.CatValidator;
import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;

import java.util.Collection;

import static com.leverx.servletapp.animal.validator.AnimalValidator.validateInputDto;
import static com.leverx.servletapp.cat.converter.CatConverter.fromInputDto;
import static com.leverx.servletapp.cat.converter.CatConverter.toDtoWithOwner;
import static com.leverx.servletapp.cat.converter.CatConverter.toOutputDtoList;

@AllArgsConstructor
public class CatServiceImpl implements CatService {

    private CatRepository catRepository;
    private CatValidator catValidator;
    private UserValidator userValidator;

    @Override
    public void save(CatInputDto catInputDto) throws ValidationException {
        validateInputDto(catInputDto);
        var cat = fromInputDto(catInputDto);
        catRepository.save(cat);
    }

    @Override
    public CatWithOwnerDto findById(int id) throws EntityNotFoundException {
        catValidator.validateId(id);
        var catOpt = catRepository.findById(id);
        var cat = catOpt.orElseThrow();
        return toDtoWithOwner(cat);
    }

    @Override
    public Collection<CatOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException {
        userValidator.validateId(ownerId);
        var cats = catRepository.findByOwnerId(ownerId);
        return toOutputDtoList(cats);
    }

    @Override
    public Collection<CatOutputDto> findAll() {
        var cats = catRepository.findAll();
        return toOutputDtoList(cats);
    }
}
