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

    public static UserOutputDto toOutputDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        return new UserOutputDto(id, firstName);
    }

    public static UserWithAnimalsDto toWithAnimalsDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var animals = user.getAnimals();
        var animalOutputDtos = AnimalConverter.toOutputDtoList(animals);
        return new UserWithAnimalsDto(id, firstName, animalOutputDtos);
    }

    public static User fromInputDto(UserInputDto userDto) {
        var firstName = userDto.getFirstName();
        var animals = new ArrayList<Animal>();
        var user = new User();
        user.setFirstName(firstName);
        user.setAnimals(animals);
        return user;
    }

    public static List<UserOutputDto> toOutputDtoList(Collection<User> users) {
        var userDtoList = new ArrayList<UserOutputDto>();

        for (var user : users) {
            var id = user.getId();
            var firstName = user.getFirstName();
            var userOutputDto = new UserOutputDto(id, firstName);
            userDtoList.add(userOutputDto);
        }
        return userDtoList;
    }
}
