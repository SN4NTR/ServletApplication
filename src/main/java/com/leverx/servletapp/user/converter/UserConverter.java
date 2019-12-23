package com.leverx.servletapp.user.converter;

import com.leverx.servletapp.animal.converter.AnimalConverter;
import com.leverx.servletapp.animal.entity.Animal;
import com.leverx.servletapp.cat.repository.CatRepository;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.dog.repository.DogRepository;
import com.leverx.servletapp.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.user.entity.User;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@NoArgsConstructor(access = PRIVATE)
public final class UserConverter {

    public static UserWithAnimalsDto toWithAnimalsDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var email = user.getEmail();
        var animalPoints = user.getAnimalPoints();
        var animals = user.getAnimals();
        var animalOutputDtos = AnimalConverter.toOutputDtoList(animals);
        return new UserWithAnimalsDto(id, firstName, email, animalPoints, animalOutputDtos);
    }

    public static User fromInputDto(UserInputDto userInputDto) {
        var firstName = userInputDto.getFirstName();
        var email = userInputDto.getEmail();
        var animalPoints = userInputDto.getAnimalPoints();
        var animals = new ArrayList<Animal>();
        var user = new User(firstName, email, animalPoints, animals);

        var catIds = userInputDto.getCatsIds();
        assignCats(user, catIds, new CatRepositoryImpl());
        var dogIds = userInputDto.getDogsIds();
        assignDogs(user, dogIds, new DogRepositoryImpl());
        return user;
    }

    public static List<UserOutputDto> toOutputDtoList(Collection<User> users) {
        var userDtoList = new ArrayList<UserOutputDto>();
        users.stream()
                .map(UserConverter::toOutputDto)
                .forEach(userDtoList::add);

        return userDtoList;
    }

    private static UserOutputDto toOutputDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var email = user.getEmail();
        return new UserOutputDto(id, firstName, email);
    }

    private static void assignCats(User user, List<Integer> catsIds, CatRepository catRepository) {
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

    private static void assignDogs(User user, List<Integer> dogsIds, DogRepository dogRepository) {
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
