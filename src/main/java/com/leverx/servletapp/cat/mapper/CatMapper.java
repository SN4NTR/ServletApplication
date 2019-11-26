package com.leverx.servletapp.cat.mapper;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CatMapper {

    public static Cat catDtoToCat(CatDto catDto) {
        var name = catDto.getName();
        var dateOfBirth = catDto.getDateOfBirth();
        return new Cat(name, dateOfBirth);
    }
}
