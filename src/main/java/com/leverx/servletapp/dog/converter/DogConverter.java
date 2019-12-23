package com.leverx.servletapp.dog.converter;

import com.leverx.servletapp.dog.dto.DogInputDto;
import com.leverx.servletapp.dog.dto.DogOutputDto;
import com.leverx.servletapp.dog.dto.DogWithOwnerDto;
import com.leverx.servletapp.dog.entity.Dog;
import com.leverx.servletapp.user.converter.UserConverter;
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
        int goodBoyAmount = dogInputDto.getGoodBoyAmount();
        return new Dog(name, dateOfBirth, goodBoyAmount);
    }

    public static DogWithOwnerDto toDtoWithOwner(Dog dog) {
        var id = dog.getId();
        var name = dog.getName();
        var dateOfBirth = dog.getDateOfBirth();
        int goodBoyAmount = dog.getGoodBoyAmount();
        var owners = dog.getOwners();
        var ownerOutputDto = UserConverter.toOutputDtoList(owners);
        return new DogWithOwnerDto(id, name, goodBoyAmount, dateOfBirth, ownerOutputDto);
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
        int goodBoyAmount = dog.getGoodBoyAmount();
        return new DogOutputDto(id, name, dateOfBirth, goodBoyAmount);
    }
}
