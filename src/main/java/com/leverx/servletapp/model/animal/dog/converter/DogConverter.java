package com.leverx.servletapp.model.animal.dog.converter;

import com.leverx.servletapp.model.animal.dog.dto.DogInputDto;
import com.leverx.servletapp.model.animal.dog.dto.DogOutputDto;
import com.leverx.servletapp.model.animal.dog.dto.DogWithOwnerDto;
import com.leverx.servletapp.model.animal.dog.entity.Dog;
import com.leverx.servletapp.model.user.converter.UserConverter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DogConverter {

    public static Dog fromInputDto(DogInputDto dogInputDto) {
        var name = dogInputDto.getName();
        var dateOfBirth = dogInputDto.getDateOfBirth();
        return new Dog(name, dateOfBirth);
    }

    public static DogWithOwnerDto toDtoWithOwner(Dog dog) {
        var id = dog.getId();
        var name = dog.getName();
        var dateOfBirth = dog.getDateOfBirth();
//        var owner = dog.getOwner();
//
//        if (nonNull(owner)) {
//            var ownerOutputDto = UserConverter.toOutputDto(owner);
//            return new DogWithOwnerDto(id, name, dateOfBirth, ownerOutputDto);
//        } else {
//            return new DogWithOwnerDto(id, name, dateOfBirth);
//        }
        return new DogWithOwnerDto(id, name, dateOfBirth);
    }

    public static List<DogOutputDto> toOutputDtoList(Collection<Dog> dogs) {
        var dogDtoList = new ArrayList<DogOutputDto>();

        for (var dog : dogs) {
            var dogDto = toOutputDto(dog);
            dogDtoList.add(dogDto);
        }
        return dogDtoList;
    }

    private static DogOutputDto toOutputDto(Dog dog) {
        var id = dog.getId();
        var name = dog.getName();
        var dateOfBirth = dog.getDateOfBirth();
        return new DogOutputDto(id, name, dateOfBirth);
    }
}
