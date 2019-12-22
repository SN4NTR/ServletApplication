package com.leverx.servletapp.user.service;

import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.cat.validator.CatValidator;
import com.leverx.servletapp.dog.repository.DogRepository;
import com.leverx.servletapp.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.dog.validator.DogValidator;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.converter.UserConverter;
import com.leverx.servletapp.user.dto.AnimalPointsDto;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.user.dto.validator.AnimalPointsValidator;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.repository.UserRepository;
import com.leverx.servletapp.user.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.leverx.servletapp.user.validator.UserValidator.validateInputDto;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CatRepository catRepository;
    private DogRepository dogRepository;

    @Override
    public void save(UserInputDto userInputDto) throws ValidationException, EntityNotFoundException {
        validateInputDto(userInputDto);

        var catsIds = userInputDto.getCatsIds();
        var dogsIds = userInputDto.getDogsIds();
        var catValidator = new CatValidator(new CatRepositoryImpl());
        catValidator.validateIds(catsIds);
        var dogValidator = new DogValidator(new DogRepositoryImpl());
        dogValidator.validateIds(dogsIds);

        var user = UserConverter.fromInputDto(userInputDto);
        assignCats(user, catsIds);
        assignDogs(user, dogsIds);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        userRepository.delete(id);
    }

    @Override
    public void update(int id, UserInputDto userInputDto) throws EntityNotFoundException, ValidationException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        validateInputDto(userInputDto);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);

        var firstName = userInputDto.getFirstName();
        user.setFirstName(firstName);
        var email = userInputDto.getEmail();
        user.setEmail(email);

        var catsIds = userInputDto.getCatsIds();
        var dogsIds = userInputDto.getDogsIds();
        var catValidator = new CatValidator(new CatRepositoryImpl());
        catValidator.validateIds(catsIds);
        var dogValidator = new DogValidator(new DogRepositoryImpl());
        dogValidator.validateIds(dogsIds);

        assignCats(user, catsIds);
        assignDogs(user, dogsIds);

        userRepository.update(user);
    }

    //TODO create points transfer
    @Override
    public void transferAnimalPoint(AnimalPointsDto animalPointsDto) throws ValidationException {
        AnimalPointsValidator.validateInputDto(animalPointsDto);
    }

    @Override
    public UserWithAnimalsDto findById(int id) throws EntityNotFoundException {
        var userValidator = new UserValidator(userRepository);
        userValidator.validateId(id);
        var userOpt = userRepository.findById(id);
        var user = userOpt.orElseThrow(EntityNotFoundException::new);
        return UserConverter.toWithAnimalsDto(user);
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
