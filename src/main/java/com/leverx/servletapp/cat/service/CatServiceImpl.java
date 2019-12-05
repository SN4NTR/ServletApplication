package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.dto.CatInputDto;
import com.leverx.servletapp.cat.entity.dto.CatOutputDto;
import com.leverx.servletapp.cat.entity.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.entity.dto.CatWithOwnerDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.InputDataException;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;

import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.cat.mapper.CatMapper.collectionToOutputDtoList;
import static com.leverx.servletapp.cat.mapper.CatMapper.fromInputDto;
import static com.leverx.servletapp.cat.mapper.CatMapper.toDtoWithOwner;
import static com.leverx.servletapp.validator.EntityValidator.validateEntity;
import static java.util.Objects.nonNull;

public class CatServiceImpl implements CatService {

    private CatRepository catRepository = new CatRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void save(CatInputDto catInputDto) throws InputDataException {
        validateEntity(catInputDto);
        var cat = fromInputDto(catInputDto);
        catRepository.save(cat);
    }

    @Override
    public void assignCat(int userId, CatWithIdsDto catWithIdsDto) throws EntityNotFoundException {
        var userOpt = userRepository.findById(userId);
        var user = userOpt.orElseThrow();
        assignCatsToUser(user, catWithIdsDto);
        userRepository.update(user);
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
        return collectionToOutputDtoList(cats);
    }

    @Override
    public Collection<CatOutputDto> findByOwnerId(int id) throws EntityNotFoundException {
        var cats = catRepository.findByOwnerId(id);
        return collectionToOutputDtoList(cats);
    }

    private void assignCatsToUser(User user, CatWithIdsDto catWithIdsDto) throws EntityNotFoundException {
        var catIds = catWithIdsDto.getIds();
        var existedCats = user.getCats();

        for (var id : catIds) {
            var catOpt = catRepository.findById(id);
            var cat = catOpt.orElseThrow();

            var owner = cat.getOwner();
            if (nonNull(owner)) {
                throw new EntityNotFoundException("Cat already has an owner");
            }

            existedCats.add(cat);
            cat.setOwner(user);
        }
    }
}
