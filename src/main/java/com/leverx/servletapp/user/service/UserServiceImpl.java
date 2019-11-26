package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.user.mapper.UserMapper.userDtoToUser;
import static com.leverx.servletapp.validator.EntityValidator.isEntityValid;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = getLogger(UserServiceImpl.class.getSimpleName());

    private UserRepository userRepository = new UserRepositoryImpl();
    private CatRepository catRepository = new CatRepositoryImpl();

    private static final int FIRST_NAME_LENGTH_MIN = 5;
    private static final int FIRST_NAME_LENGTH_MAX = 60;

    @Override
    public void save(UserDto userDto) {
        if (isEntityValid(userDto)) {
            var user = userDtoToUser(userDto);

            var catIdList = userDto.getCatsIdList();
            var catList = new ArrayList<Cat>();
            for (Integer id : catIdList) {
                var cat = catRepository.findById(id);

                if (cat != null) {
                    catList.add(cat);
                    user.setCats(catList);
                    cat.setOwner(user);
                }
            }
            userRepository.save(user);
        } else {
            var message = format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            LOGGER.error(message);
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
        var users = userRepository.findAll();

        return users.stream()
                .peek(user -> user.setCats(null))
                .collect(toList());
    }

    @Override
    public Collection<Cat> findCatsByUserId(int id) {
        var user = userRepository.findById(id);
        var cats = user.getCats();

        return cats.stream()
                .peek(cat -> cat.setOwner(null))
                .collect(toList());
    }

    @Override
    public void delete(int id) {
        var user = userRepository.findById(id);
        userRepository.delete(user);
    }

    @Override
    public void assignCat(int userId, CatDto catDto) {
        var user = userRepository.findById(userId);
        var catId = catDto.getId();
        var cat = catRepository.findById(catId);

        if (cat == null) {
            var message = format("Cat with id = %s not found", catId);
            LOGGER.error(message);
        } else {
            var owner = cat.getOwner();
            if (owner != null) {
                var message = format("Cat with id = %s has owner", catId);
                LOGGER.error(message);
            } else {
                cat.setOwner(user);
                var cats = List.of(cat);
                user.setCats(cats);
                userRepository.update(user);
            }
        }
    }

    @Override
    public void update(int id, UserDto userDto) {
        if (isEntityValid(userDto)) {
            var user = userDtoToUser(userDto);
            user.setId(id);
            userRepository.update(user);
        } else {
            var message = format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
