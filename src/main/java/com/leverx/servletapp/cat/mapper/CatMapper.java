package com.leverx.servletapp.cat.mapper;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import lombok.NoArgsConstructor;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CatMapper {

    public static Cat catDtoToCat(CatDto catDto) {
        var name = catDto.getName();
        var dateOfBirth = catDto.getDateOfBirth();

        var cat = new Cat();
        cat.setName(name);
        cat.setDateOfBirth(dateOfBirth);

        return cat;
    }

    public static Collection<Cat> catsWithoutOwners(Collection<Cat> cats) {
        return cats.stream()
                .peek(cat -> cat.setOwner(null))
                .collect(toList());
    }
}
