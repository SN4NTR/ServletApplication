package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.leverx.servletapp.cat.mapper.CatMapper.catCollectionToDtoList;
import static com.leverx.servletapp.user.mapper.UserMapper.userCollectionToDtoList;
import static com.leverx.servletapp.user.mapper.UserMapper.userDtoToUser;
import static com.leverx.servletapp.user.mapper.UserMapper.userToDtoWithCats;
import static com.leverx.servletapp.validator.EntityValidator.isEntityValid;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
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
            var message = format("Length of first name must be between %s and %s", FIRST_NAME_LENGTH_MIN, FIRST_NAME_LENGTH_MAX);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public UserDto findById(int id) {
        var user = userRepository.findById(id);
        return nonNull(user) ? userToDtoWithCats(user) : null;
    }

    @Override
    public Collection<UserDto> findByName(String name) {
        var users = userRepository.findByName(name);
        return userCollectionToDtoList(users);
    }

    @Override
    public Collection<UserDto> findAll() {
        var users = userRepository.findAll();
        return userCollectionToDtoList(users);
    }

    @Override
    public Collection<CatDto> findCatsByUserId(int id) {
        var user = userRepository.findById(id);
        var cats = user.getCats();
        return catCollectionToDtoList(cats);
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
        if (nonNull(catsIdList)) {
            var catList = user.getCats();

            var catsFromIdList = catsIdList.stream()
                    .map(catRepository::findById)
                    .filter(Objects::nonNull)
                    .filter(cat -> isNull(cat.getOwner()))
                    .peek(cat -> cat.setOwner(user))
                    .collect(toList());

            catList.addAll(catsFromIdList);
        }
    }
}
