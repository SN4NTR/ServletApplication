package com.leverx.servletapp.user.service;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;

import java.util.Collection;

import static com.leverx.servletapp.user.mapper.UserMapper.userDtoToUser;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();

    private static final int FIRST_NAME_LENGTH = 60;

    @Override
    public void save(UserDto userDto) {
        var firstName = userDto.getFirstName();

        if (isFirstNameLengthValid(firstName)) {
            var user = userDtoToUser(userDto);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("First name length must be lower than " + FIRST_NAME_LENGTH);
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
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserDto userDto) {
        var firstName = userDto.getFirstName();

        if (isFirstNameLengthValid(firstName)) {
            var user = userDtoToUser(userDto);
            user.setId(id);
            userRepository.update(user);
        } else  {
            throw new IllegalArgumentException("First name length must be lower than " + FIRST_NAME_LENGTH);
        }
    }

    private boolean isFirstNameLengthValid(String firstName) {
        return firstName.length() <= FIRST_NAME_LENGTH;
    }
}
