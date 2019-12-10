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

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DogConverter {

    public static Dog fromInputDto(DogInputDto dogInputDto) {
        var name = dogInputDto.getName();
        var dateOfBirth = dogInputDto.getDateOfBirth();
        var dog = new Dog();
        dog.setName(name);
        dog.setDateOfBirth(dateOfBirth);
        return dog;
    }

    public static DogWithOwnerDto toDtoWithOwner(Dog dog) {
        var id = dog.getId();
        var name = dog.getName();
        var dateOfBirth = dog.getDateOfBirth();
        var owners = dog.getOwners();
        var ownerOutputDto = UserConverter.toOutputDtoList(owners);
        return new DogWithOwnerDto(id, name, dateOfBirth, ownerOutputDto);
    }

    public static List<DogOutputDto> toOutputDtoList(Collection<Dog> dogs) {
        var dogDtoList = new ArrayList<DogOutputDto>();
        dogs.stream()
                .map(DogConverter::toOutputDto)
                .forEach(dogDtoList::add);

        return dogDtoList;
    }

    private static DogOutputDto toOutputDto(Dog dog) {
        var id = dog.getId();
        var name = dog.getName();
        var dateOfBirth = dog.getDateOfBirth();
        var dogOutputDto = new DogOutputDto();
        dogOutputDto.setId(id);
        dogOutputDto.setName(name);
        dogOutputDto.setDateOfBirth(dateOfBirth);
        return dogOutputDto;
    }
}
