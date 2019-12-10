package com.leverx.servletapp.model.user.service;

import com.leverx.servletapp.model.animal.cat.repository.CatRepository;
import com.leverx.servletapp.model.animal.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.model.user.converter.UserConverter;
import com.leverx.servletapp.model.user.dto.UserInputDto;
import com.leverx.servletapp.model.user.dto.UserOutputDto;
import com.leverx.servletapp.model.user.dto.UserWithCatsDto;
import com.leverx.servletapp.model.user.repository.UserRepository;
import com.leverx.servletapp.model.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.user.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.validateIds;
import static com.leverx.servletapp.model.user.validator.UserValidator.validateId;
import static com.leverx.servletapp.model.user.validator.UserValidator.validateInputDto;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();
    private CatRepository catRepository = new CatRepositoryImpl();

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        validateInputDto(userInputDto);
        var catIds = userInputDto.getCatIds();
        validateIds(catIds);
        var user = UserConverter.fromInputDto(userInputDto);
        userRepository.save(user);
        addCats(user, catIds);
        userRepository.update(user);
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        validateInputDto(userInputDto);
        validateId(id);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow();
        var firstName = userInputDto.getFirstName();
        user.setFirstName(firstName);
        userRepository.update(user);
    }

    @Override
    public UserWithCatsDto findById(int id) throws EntityNotFoundException {
        validateId(id);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        return UserConverter.toWithCatsDto(user);
    }

    @Override
    public Collection<UserOutputDto> findByName(String name) {
        var users = userRepository.findByName(name);
        return UserConverter.toOutputDtoList(users);
    }

    @Override
    public Collection<UserOutputDto> findAll() {
        var users = userRepository.findAll();
        return UserConverter.toOutputDtoList(users);
    }

    private void addCats(User user, List<Integer> catIds) {
        if (isNotEmpty(catIds)) {
            var existingCats = user.getCats();
            catIds.stream()
                    .map(catRepository::findById)
                    .map(Optional::orElseThrow)
                    .peek(existingCats::add)
                    .forEach(cat -> cat.setOwner(user));
        }
    }
}
