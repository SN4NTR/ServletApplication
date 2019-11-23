package com.leverx.servletapp.cat.mapper;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;

public class CatMapper {

    public static Cat catDtoToCat(CatDto catDto) {
        var cat = new Cat();
        var name = catDto.getName();
        cat.setName(name);
        var dateOfBirth = catDto.getDateOfBirth();
        cat.setDateOfBirth(dateOfBirth);
        return cat;
    }
}
