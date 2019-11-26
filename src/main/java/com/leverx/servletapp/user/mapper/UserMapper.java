package com.leverx.servletapp.user.mapper;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UserMapper {

    public static User userDtoToUser(UserDto userDto) {
        var firstName = userDto.getFirstName();
        var user = new User();
        user.setFirstName(firstName);
        return user;
    }
}
