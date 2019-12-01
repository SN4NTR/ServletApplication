package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.validator.EntityValidator.isEntityValid;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

// TODO simplify methods

@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private CatRepository catRepository = new CatRepositoryImpl();

    private static final int FIRST_NAME_LENGTH_MIN = 5;
    private static final int FIRST_NAME_LENGTH_MAX = 60;

    @Override
    public void save(UserDto userDto) {
        if (isEntityValid(userDto)) {
            var firstName = userDto.getFirstName();
            var user = new User(firstName);
            var catsIdList = userDto.getCatsIdList();
            user.setCats(new ArrayList<>());

            setCatList(user, catsIdList);

            userRepository.save(user);
        } else {
            var message = format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public UserDto findById(int id) {
        var user = userRepository.findById(id);
        var firstName = user.getFirstName();
        var cats = user.getCats();
        var catDtoList = new ArrayList<CatDto>();

        for (var cat : cats) {
            var catId = cat.getId();
            var catName = cat.getName();
            var catDateOfBirth = cat.getDateOfBirth();

            var catDto = new CatDto();
            catDto.setId(catId);
            catDto.setName(catName);
            catDto.setDateOfBirth(catDateOfBirth);

            catDtoList.add(catDto);
        }

        var userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setCats(catDtoList);

        return userDto;
    }

    @Override
    public Collection<UserDto> findByName(String name) {
        var users = userRepository.findByName(name);
        var userDtoList = new ArrayList<UserDto>();

        for (var user : users) {
            var id = user.getId();
            var firstName = user.getFirstName();
            var cats = user.getCats();
            var catDtoList = new ArrayList<CatDto>();

            for (var cat : cats) {
                var catId = cat.getId();
                var catName = cat.getName();
                var catDateOfBirth = cat.getDateOfBirth();

                var catDto = new CatDto();
                catDto.setId(catId);
                catDto.setName(catName);
                catDto.setDateOfBirth(catDateOfBirth);

                catDtoList.add(catDto);
            }

            var userDto = new UserDto();
            userDto.setId(id);
            userDto.setFirstName(firstName);
            userDto.setCats(catDtoList);

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public Collection<UserDto> findAll() {
        var users = userRepository.findAll();
        var userDtoList = new ArrayList<UserDto>();

        for (var user : users) {
            var id = user.getId();
            var firstName = user.getFirstName();

            var userDto = new UserDto();
            userDto.setId(id);
            userDto.setFirstName(firstName);

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public Collection<Cat> findCatsByUserId(int id) {
        var user = userRepository.findById(id);
        return user.getCats();
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void assignCat(int userId, CatDto catDto) {
        var user = userRepository.findById(userId);
        var catsIdList = catDto.getIdList();
        setCatList(user, catsIdList);
        userRepository.update(user);
    }

    @Override
    public void update(int id, UserDto userDto) {
        if (isEntityValid(userDto)) {
            var user = userRepository.findById(id);
            var firstName = userDto.getFirstName();
            user.setFirstName(firstName);

            var catsIdList = userDto.getCatsIdList();
            setCatList(user, catsIdList);

            userRepository.update(user);
        } else {
            var message = format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void setCatList(User user, List<Integer> catsIdList) {
        if (!isNull(catsIdList)) {
            var catList = user.getCats();

            var catsFromIdList = catsIdList.stream()
                    .map(catRepository::findById)
                    .filter(cat -> !isNull(cat))
                    .filter(cat -> isNull(cat.getOwner()))
                    .peek(cat -> cat.setOwner(user))
                    .collect(toList());

            catList.addAll(catsFromIdList);
        }
    }
}
