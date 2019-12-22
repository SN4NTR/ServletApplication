package com.leverx.servletapp.model.animal.cat.service;

import com.leverx.servletapp.annotation.Service;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.animal.cat.dto.CatInputDto;
import com.leverx.servletapp.model.animal.cat.dto.CatOutputDto;
import com.leverx.servletapp.model.animal.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.model.animal.cat.repository.CatRepository;
import com.leverx.servletapp.model.user.validator.UserValidator;
import lombok.AllArgsConstructor;

import java.util.Collection;

import static com.leverx.servletapp.model.animal.cat.converter.CatConverter.fromInputDto;
import static com.leverx.servletapp.model.animal.cat.converter.CatConverter.toDtoWithOwner;
import static com.leverx.servletapp.model.animal.cat.converter.CatConverter.toOutputDtoList;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.validateId;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.validateInputDto;

@Service
@AllArgsConstructor
public class CatServiceImpl implements CatService {

    private CatRepository catRepository;

    @Override
    public void save(CatInputDto catInputDto) throws ValidationException {
        validateInputDto(catInputDto);
        var cat = fromInputDto(catInputDto);
        catRepository.save(cat);
    }

    @Override
    public CatWithOwnerDto findById(int id) throws EntityNotFoundException {
        validateId(id);
        var catOpt = catRepository.findById(id);
        var cat = catOpt.orElseThrow();
        return toDtoWithOwner(cat);
    }

    @Override
    public Collection<CatOutputDto> findByOwnerId(int ownerId) throws EntityNotFoundException {
        UserValidator.validateId(ownerId);
        var cats = catRepository.findByOwnerId(ownerId);
        return toOutputDtoList(cats);
    }

    @Override
    public Collection<CatOutputDto> findAll() {
        var cats = catRepository.findAll();
        return toOutputDtoList(cats);
    }
}
