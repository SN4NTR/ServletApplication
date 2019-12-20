package com.leverx.servletapp.model.user.converter;

import com.leverx.servletapp.model.animal.parent.Animal;
import com.leverx.servletapp.model.animal.parent.converter.AnimalConverter;
import com.leverx.servletapp.model.user.dto.UserInputDto;
import com.leverx.servletapp.model.user.dto.UserOutputDto;
import com.leverx.servletapp.model.user.dto.UserWithAnimalsDto;
import com.leverx.servletapp.model.user.entity.User;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserConverter {

    private static UserOutputDto toOutputDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var email = user.getEmail();
        return new UserOutputDto(id, firstName, email);
    }

    public static UserWithAnimalsDto toWithAnimalsDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var email = user.getEmail();
        var animals = user.getAnimals();
        var animalOutputDtos = AnimalConverter.toOutputDtoList(animals);
        return new UserWithAnimalsDto(id, firstName, email, animalOutputDtos);
    }

    public static User fromInputDto(UserInputDto userInputDto) {
        var firstName = userInputDto.getFirstName();
        var email = userInputDto.getEmail();
        var animals = new ArrayList<Animal>();
        return new User(firstName, email, animals);
    }

    public static List<UserOutputDto> toOutputDtoList(Collection<User> users) {
        var userDtoList = new ArrayList<UserOutputDto>();
        users.stream()
                .map(UserConverter::toOutputDto)
                .forEach(userDtoList::add);

        return userDtoList;
    }
}
