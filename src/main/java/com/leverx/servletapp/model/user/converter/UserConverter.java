package com.leverx.servletapp.model.user.converter;

import com.leverx.servletapp.model.animal.cat.converter.CatConverter;
import com.leverx.servletapp.model.animal.cat.entity.Cat;
import com.leverx.servletapp.model.user.entity.User;
import com.leverx.servletapp.model.user.dto.UserInputDto;
import com.leverx.servletapp.model.user.dto.UserOutputDto;
import com.leverx.servletapp.model.user.dto.UserWithCatsDto;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserConverter {

    public static UserOutputDto toOutputDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        return new UserOutputDto(id, firstName);
    }

    public static UserWithCatsDto toWithCatsDto(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var cats = user.getCats();
        var catOutputDtoList = CatConverter.toOutputDtoList(cats);
        return new UserWithCatsDto(id, firstName, catOutputDtoList);
    }

    public static User fromInputDto(UserInputDto userDto) {
        var firstName = userDto.getFirstName();
        var cats = new ArrayList<Cat>();
        var user = new User();
        user.setFirstName(firstName);
        user.setCats(cats);
        return user;
    }

    public static List<UserOutputDto> toOutputDtoList(Collection<User> users) {
        var userDtoList = new ArrayList<UserOutputDto>();

        for (var user : users) {
            var id = user.getId();
            var firstName = user.getFirstName();
            var userDto = new UserOutputDto(id, firstName);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
