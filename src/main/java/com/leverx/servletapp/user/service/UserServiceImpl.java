package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDtoId;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;

import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.user.mapper.UserMapper.userDtoToUser;
import static com.leverx.servletapp.validator.EntityValidator.isEntityValid;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private CatRepository catRepository = new CatRepositoryImpl();

    private static final int FIRST_NAME_LENGTH_MIN = 5;
    private static final int FIRST_NAME_LENGTH_MAX = 60;

    @Override
    public void save(UserDto userDto) {
        if (isEntityValid(userDto)) {
            var user = userDtoToUser(userDto);
            userRepository.save(user);
        } else {
            String message = String.format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Collection<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Collection<Cat> findCatsByUserId(int id) {
        var user = userRepository.findById(id);
        return user != null ? user.getCats() : null;
    }

    @Override
    public void delete(int id) {
        var user = new User();
        user.setId(id);
        userRepository.delete(user);
    }

    @Override
    public void assignCat(int userId, CatDtoId catDtoId) {
        var user = userRepository.findById(userId);
        var catId = catDtoId.getId();
        var cat = catRepository.findById(catId);
        cat.setOwner(user);
        var cats = List.of(cat);
        user.setCats(cats);
        userRepository.update(user);
    }

    @Override
    public void update(int id, UserDto userDto) {
        var user = userDtoToUser(userDto);

        if (isEntityValid(user)) {
            user.setId(id);
            userRepository.update(user);
        } else  {
            String message = String.format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            throw new IllegalArgumentException(message);
        }
    }
}
