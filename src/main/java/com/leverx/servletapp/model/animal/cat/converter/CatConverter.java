package com.leverx.servletapp.model.animal.cat.converter;

import com.leverx.servletapp.model.animal.cat.dto.CatInputDto;
import com.leverx.servletapp.model.animal.cat.dto.CatOutputDto;
import com.leverx.servletapp.model.animal.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.model.animal.cat.entity.Cat;
import com.leverx.servletapp.model.user.converter.UserConverter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CatConverter {

    public static Cat fromInputDto(CatInputDto catDto) {
        var name = catDto.getName();
        var dateOfBirth = catDto.getDateOfBirth();
        var cat = new Cat();
        cat.setName(name);
        cat.setDateOfBirth(dateOfBirth);
        return cat;
    }

    public static CatWithOwnerDto toDtoWithOwner(Cat cat) {
        var id = cat.getId();
        var name = cat.getName();
        var dateOfBirth = cat.getDateOfBirth();
        var owners = cat.getOwners();
        var ownerOutputDto = UserConverter.toOutputDtoList(owners);
        return new CatWithOwnerDto(id, name, dateOfBirth, ownerOutputDto);
    }

    public static List<CatOutputDto> toOutputDtoList(Collection<Cat> cats) {
        var catDtoList = new ArrayList<CatOutputDto>();

        for (var cat : cats) {
            var catDto = toOutputDto(cat);
            catDtoList.add(catDto);
        }
        return catDtoList;
    }

    private static CatOutputDto toOutputDto(Cat cat) {
        var id = cat.getId();
        var name = cat.getName();
        var dateOfBirth = cat.getDateOfBirth();
        var catOutputDto = new CatOutputDto();
        catOutputDto.setId(id);
        catOutputDto.setName(name);
        catOutputDto.setDateOfBirth(dateOfBirth);
        return catOutputDto;
    }
}
