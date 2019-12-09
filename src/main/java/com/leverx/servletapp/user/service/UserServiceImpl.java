package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithCatsDto;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.user.converter.UserConverter.fromInputDto;
import static com.leverx.servletapp.user.converter.UserConverter.toOutputDtoList;
import static com.leverx.servletapp.user.converter.UserConverter.toWithCatsDto;
import static com.leverx.servletapp.validator.EntityValidator.validateEntity;

@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private CatRepository catRepository = new CatRepositoryImpl();

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException {
        validateEntity(userInputDto);
        var user = fromInputDto(userInputDto);
        userRepository.save(user);
        var catIds = userInputDto.getCatIds();
        addCats(user, catIds);
        userRepository.update(user);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        validateEntity(userInputDto);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        var firstName = userInputDto.getFirstName();
        user.setFirstName(firstName);
        userRepository.update(user);
    }

    @Override
    public UserWithCatsDto findById(int id) throws EntityNotFoundException {
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        return toWithCatsDto(user);
    }

    @Override
    public Collection<UserOutputDto> findByName(String name) {
        var users = userRepository.findByName(name);
        return toOutputDtoList(users);
    }

    @Override
    public Collection<UserOutputDto> findAll() {
        var users = userRepository.findAll();
        return toOutputDtoList(users);
    }

    private void addCats(User user, List<Integer> ids) {
        var existingCats = user.getCats();
        for (var id : ids) {
            var catOpt = catRepository.findById(id);
            var cat = catOpt.orElseThrow();
            existingCats.add(cat);
            cat.setOwner(user);
        }
    }
}
