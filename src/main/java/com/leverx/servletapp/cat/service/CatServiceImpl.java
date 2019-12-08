package com.leverx.servletapp.cat.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.dto.CatOutputDto;
import com.leverx.servletapp.cat.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.InputDataException;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.leverx.servletapp.cat.mapper.CatConverter.toOutputDtoList;
import static com.leverx.servletapp.cat.mapper.CatConverter.fromInputDto;
import static com.leverx.servletapp.cat.mapper.CatConverter.toDtoWithOwner;
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
    public void assignToUser(int userId, CatWithIdsDto catWithIdsDto) throws EntityNotFoundException {
        var userOpt = userRepository.findById(userId);
        var user = userOpt.orElseThrow();
        assignCatsToUser(user, catWithIdsDto);
        userRepository.update(user);
    }

    @Override
    public void transfer(int userIdFrom, int userIdTo, Collection<Integer> catIds) throws EntityNotFoundException {
        var userFromOpt = userRepository.findById(userIdFrom);
        var userFrom = userFromOpt.orElseThrow();
        var userToOpt = userRepository.findById(userIdTo);
        var userTo = userToOpt.orElseThrow();
        transferCats(catIds, userFrom, userTo);
        userRepository.update(userFrom);
        userRepository.update(userTo);
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

    private synchronized void transferCats(Collection<Integer> catIds, User userFrom, User userTo) throws EntityNotFoundException {
        var catsOfUserFrom = userFrom.getCats();
        var newCatsOfUserFrom = new ArrayList<Cat>();

        var existingCatsOfUserTo = userTo.getCats();
        var newCatsOfUserTo = new ArrayList<Cat>();

        for (var cat : catsOfUserFrom) {
            for (var id : catIds) {
                var catId = cat.getId();

                if (id == catId) {
                    cat.setOwner(userTo);
                    newCatsOfUserTo.add(cat);
                } else {
                    newCatsOfUserFrom.add(cat);
                }
            }
        }

        if (newCatsOfUserTo.size() != catIds.size()) {
            throw new EntityNotFoundException("Cat can't be found");
        }
        userFrom.setCats(newCatsOfUserFrom);
        existingCatsOfUserTo.addAll(newCatsOfUserTo);
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
