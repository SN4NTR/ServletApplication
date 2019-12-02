package com.leverx.servletapp.cat.mapper;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.user.mapper.UserMapper.userToDto;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CatMapper {

    public static Cat catDtoToCat(CatDto catDto) {
        var name = catDto.getName();
        var dateOfBirth = catDto.getDateOfBirth();
        return new Cat(name, dateOfBirth);
    }

    public static List<CatDto> catCollectionToDtoList(Collection<Cat> cats) {
        var catDtoList = new ArrayList<CatDto>();

        for (var cat : cats) {
            var catDto = catToDto(cat);
            catDtoList.add(catDto);
        }
        return catDtoList;
    }

    public static CatDto catToDtoWithOwner(Cat cat) {
        var owner = cat.getOwner();
        var ownerDto = userToDto(owner);

        var catDto = catToDto(cat);
        catDto.setOwner(ownerDto);

        return catDto;
    }

    private static CatDto catToDto(Cat cat) {
        var id = cat.getId();
        var name = cat.getName();
        var dateOfBirth = cat.getDateOfBirth();

        var catDto = new CatDto();
        catDto.setId(id);
        catDto.setName(name);
        catDto.setDateOfBirth(dateOfBirth);

        return catDto;
    }
}
