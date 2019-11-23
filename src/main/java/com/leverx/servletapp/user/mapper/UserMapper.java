package com.leverx.servletapp.user.mapper;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User userDtoToUser(UserDto userDto) {
        var firstName = userDto.getFirstName();
        var user = new User();
        user.setFirstName(firstName);
        return user;
    }
}
