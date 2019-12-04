package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.dto.CatInputDto;
import com.leverx.servletapp.cat.entity.dto.CatOutputDto;
import com.leverx.servletapp.cat.entity.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.entity.dto.CatWithOwnerDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;

import java.util.Collection;
import java.util.NoSuchElementException;

import static com.leverx.servletapp.cat.mapper.CatMapper.collectionToOutputDtoList;
import static com.leverx.servletapp.cat.mapper.CatMapper.fromInputDto;
import static com.leverx.servletapp.cat.mapper.CatMapper.toDtoWithOwner;
import static com.leverx.servletapp.validator.EntityValidator.validateEntity;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class CatServiceImpl implements CatService {

    private CatRepository catRepository = new CatRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(CatInputDto catInputDto) {
        validateEntity(catInputDto);
        var cat = fromInputDto(catInputDto);
        catRepository.save(cat);
    }

    @Override
    public void assignCat(int userId, CatWithIdsDto catWithIdsDto) {
        var userOpt = userRepository.findById(userId);
        var user = userOpt.orElseThrow();
        var cats = user.getCats();
        var catIds = catWithIdsDto.getIds();
        var newCats = catIds.stream()
                .map(this::findCatForAssigning)
                .collect(toList());

        newCats.forEach(cat -> cat.setOwner(user));
        cats.addAll(newCats);
        user.setCats(cats);
        userRepository.update(user);
    }

    @Override
    public CatWithOwnerDto findById(int id) throws NoSuchElementException {
        var catOtp = catRepository.findById(id);
        var cat = catOtp.orElseThrow();
        return toDtoWithOwner(cat);
    }

    @Override
    public Collection<CatOutputDto> findAll() {
        var cats = catRepository.findAll();
        return collectionToOutputDtoList(cats);
    }

    @Override
    public Collection<CatOutputDto> findByOwnerId(int id) {
        var cats = catRepository.findByOwnerId(id);
        return collectionToOutputDtoList(cats);
    }

    private Cat findCatForAssigning(int id) throws NoSuchElementException {
        var catOpt = catRepository.findById(id);
        var cat = catOpt.orElseThrow();

        var owner = cat.getOwner();
        if (isNull(owner)) {
            return cat;
        } else {
            throw new IllegalArgumentException("Cat already has owner");
        }
    }
}
