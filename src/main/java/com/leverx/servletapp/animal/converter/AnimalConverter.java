package com.leverx.servletapp.animal.converter;

import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import com.leverx.servletapp.animal.dto.AnimalWithOwnerDto;
import com.leverx.servletapp.animal.entity.Animal;
import com.leverx.servletapp.user.converter.UserConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnimalConverter {

    public static List<AnimalOutputDto> toOutputDtoList(Collection<Animal> animals) {
        var animalOutputDtos = new ArrayList<AnimalOutputDto>();
        animals.stream()
                .map(AnimalConverter::toOutputDto)
                .forEach(animalOutputDtos::add);

        return animalOutputDtos;
    }

    private static AnimalOutputDto toOutputDto(Animal animal) {
        var id = animal.getId();
        var name = animal.getName();
        var dateOfBirth = animal.getDateOfBirth();
        return new AnimalOutputDto(id, name, dateOfBirth);
    }

    public static AnimalWithOwnerDto toDtoWithOwner(Animal animal) {
        var id = animal.getId();
        var name = animal.getName();
        var dateOfBirth = animal.getDateOfBirth();
        var owners = animal.getOwners();
        var ownerOutputDto = UserConverter.toOutputDtoList(owners);
        return new AnimalWithOwnerDto(id, name, dateOfBirth, ownerOutputDto);
    }
}
