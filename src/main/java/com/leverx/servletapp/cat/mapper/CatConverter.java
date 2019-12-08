package com.leverx.servletapp.cat.mapper;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.dto.CatOutputDto;
import com.leverx.servletapp.cat.dto.CatWithOwnerDto;
import com.leverx.servletapp.user.mapper.UserConverter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CatConverter {

    public static Cat fromInputDto(CatInputDto catDto) {
        var name = catDto.getName();
        var dateOfBirth = catDto.getDateOfBirth();
        return new Cat(name, dateOfBirth);
    }

    public static CatWithOwnerDto toDtoWithOwner(Cat cat) {
        var id = cat.getId();
        var name = cat.getName();
        var dateOfBirth = cat.getDateOfBirth();
        var owner = cat.getOwner();

        if (nonNull(owner)) {
            var ownerOutputDto = UserConverter.toOutputDto(owner);
            return new CatWithOwnerDto(id, name, dateOfBirth, ownerOutputDto);
        } else {
            return new CatWithOwnerDto(id, name, dateOfBirth);
        }
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
        return new CatOutputDto(id, name, dateOfBirth);
    }
}
