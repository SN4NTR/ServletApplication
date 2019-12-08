package com.leverx.servletapp.user.mapper;

import com.leverx.servletapp.cat.mapper.CatMapper;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.dto.UserOutputDto;
import com.leverx.servletapp.user.dto.UserWithCatsDto;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserMapper {

    public static UserOutputDto toOutputDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        return new UserOutputDto(id, firstName);
    }

    public static UserWithCatsDto toWithCatsDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var cats = user.getCats();
        var catOutputDtoList = CatMapper.collectionToOutputDtoList(cats);
        return new UserWithCatsDto(id, firstName, catOutputDtoList);
    }

    public static User fromInputDto(UserInputDto userDto) {
        var firstName = userDto.getFirstName();
        return new User(firstName);
    }

    public static List<UserOutputDto> collectionToOutputDtoList(Collection<User> users) {
        var userDtoList = new ArrayList<UserOutputDto>();

        for (var user : users) {
            var id = user.getId();
            var firstName = user.getFirstName();

            var userDto = new UserOutputDto();
            userDto.setId(id);
            userDto.setFirstName(firstName);

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

//    public static UserOutputDto toDtoWithCats(User user) {
//        var id = user.getId();
//        var firstName = user.getFirstName();
//        var cats = user.getCats();
//        var catDtoList = catCollectionToDtoList(cats);
//
//        var userDto = new UserOutputDto();
//        userDto.setId(id);
//        userDto.setFirstName(firstName);
//        userDto.setCats(catDtoList);
//
//        return userDto;
//    }
}
