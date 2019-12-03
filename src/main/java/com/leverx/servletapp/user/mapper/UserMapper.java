package com.leverx.servletapp.user.mapper;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.cat.mapper.CatMapper.catCollectionToDtoList;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserMapper {

    public static User userDtoToUser(UserDto userDto) {
        var firstName = userDto.getFirstName();
        return new User(firstName);
    }

    public static List<UserDto> userCollectionToDtoList(Collection<User> users) {
        var userDtoList = new ArrayList<UserDto>();

        for (var user : users) {
            var id = user.getId();
            var firstName = user.getFirstName();

            var userDto = new UserDto();
            userDto.setId(id);
            userDto.setFirstName(firstName);

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public static UserDto userToDtoWithCats(User user) {
        var id = user.getId();
        var firstName = user.getFirstName();
        var cats = user.getCats();
        var catDtoList = catCollectionToDtoList(cats);

        var userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName(firstName);
        userDto.setCats(catDtoList);

        return userDto;
    }

    public static UserDto userToDto(User user) {
        if (nonNull(user)) {
            var id = user.getId();
            var firstName = user.getFirstName();

            var userDto = new UserDto();
            userDto.setId(id);
            userDto.setFirstName(firstName);

            return userDto;
        } else {
            return null;
        }
    }
}
