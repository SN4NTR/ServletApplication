package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;

import java.util.Collection;

import static com.leverx.servletapp.cat.mapper.CatMapper.catCollectionToDtoList;
import static com.leverx.servletapp.cat.mapper.CatMapper.catDtoToCat;
import static com.leverx.servletapp.cat.mapper.CatMapper.catToDtoWithOwner;
import static com.leverx.servletapp.validator.EntityValidator.isEntityValid;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

public class CatServiceImpl implements CatService {

    private CatRepository catRepository = new CatRepositoryImpl();

    private static final int NAME_LENGTH_MIN = 5;
    private static final int NAME_LENGTH_MAX = 60;

    @Override
    public void save(CatDto catDto) {
        if (isEntityValid(catDto)) {
            var cat = catDtoToCat(catDto);
            catRepository.save(cat);
        } else {
            String message = format(
                    "Length of name must be between %s and %s\n" +
                    "or date of birth can't be bigger than today's date.",
                    NAME_LENGTH_MIN, NAME_LENGTH_MAX);

            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public CatDto findById(int id) {
        var cat = catRepository.findById(id);
        return nonNull(cat) ? catToDtoWithOwner(cat) : null;
    }

    @Override
    public Collection<CatDto> findAll() {
        var cats = catRepository.findAll();
        return catCollectionToDtoList(cats);
    }
}
