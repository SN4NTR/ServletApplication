package com.leverx.servletapp.model.user.service;

import com.leverx.servletapp.annotation.Service;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.model.animal.cat.repository.CatRepository;
import com.leverx.servletapp.model.animal.cat.validator.CatValidator;
import com.leverx.servletapp.model.animal.dog.repository.DogRepository;
import com.leverx.servletapp.model.animal.dog.validator.DogValidator;
import com.leverx.servletapp.model.user.dto.UserInputDto;
import com.leverx.servletapp.model.user.dto.UserOutputDto;
import com.leverx.servletapp.model.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.model.user.entity.User;
import com.leverx.servletapp.model.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.leverx.servletapp.context.ApplicationContext.getBean;
import static com.leverx.servletapp.model.user.converter.UserConverter.fromInputDto;
import static com.leverx.servletapp.model.user.converter.UserConverter.toOutputDtoList;
import static com.leverx.servletapp.model.user.converter.UserConverter.toWithAnimalsDto;
import static com.leverx.servletapp.model.user.validator.UserValidator.validateId;
import static com.leverx.servletapp.model.user.validator.UserValidator.validateInputDto;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CatRepository catRepository;
    private DogRepository dogRepository;

    public UserServiceImpl() {
        userRepository = getBean(UserRepository.class);
        catRepository = getBean(CatRepository.class);
        dogRepository = getBean(DogRepository.class);
    }

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        validateInputDto(userInputDto);
        var catsIds = userInputDto.getCatsIds();
        var dogsIds = userInputDto.getDogsIds();
        CatValidator.validateIds(catsIds);
        DogValidator.validateIds(dogsIds);
        var user = fromInputDto(userInputDto);
        assignCats(user, catsIds);
        assignDogs(user, dogsIds);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        validateId(id);
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        validateId(id);
        validateInputDto(userInputDto);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        var firstName = userInputDto.getFirstName();
        user.setFirstName(firstName);
        userRepository.update(user);
    }

    @Override
    public UserWithAnimalsDto findById(int id) throws EntityNotFoundException {
        validateId(id);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        return toWithAnimalsDto(user);
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

    private void assignCats(User user, List<Integer> catsIds) {
        if (isNotEmpty(catsIds)) {
            var existingAnimals = user.getAnimals();
            catsIds.stream()
                    .map(catRepository::findById)
                    .map(Optional::orElseThrow)
                    .peek(existingAnimals::add)
                    .forEach(cat -> {
                        var owners = cat.getOwners();
                        owners.add(user);
                    });
        }
    }

    private void assignDogs(User user, List<Integer> dogsIds) {
        if (isNotEmpty(dogsIds)) {
            var existingAnimals = user.getAnimals();
            dogsIds.stream()
                    .map(dogRepository::findById)
                    .map(Optional::orElseThrow)
                    .peek(existingAnimals::add)
                    .forEach(dog -> {
                        var owners = dog.getOwners();
                        owners.add(user);
                    });
        }
    }
}
