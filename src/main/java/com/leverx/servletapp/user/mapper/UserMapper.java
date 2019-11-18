package com.leverx.servletapp.user.mapper;

import com.google.gson.Gson;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    private final static Gson GSON = new Gson();

    public static String readJsonBody(BufferedReader reader) {
        return reader.lines()
                .collect(joining());
    }

    public static UserDto jsonToUserDto(String jsonBody) {
        return GSON.fromJson(jsonBody, UserDto.class);
    }

    public static String userToJson(User user) {
        return GSON.toJson(user);
    }

    public static String collectionToJson(Collection<User> users) {
        return users.stream()
                .map(UserMapper::userToJson)
                .collect(joining("\n"));
    }

    public static User userDtoToUser(UserDto userDto) {
        var firstName = userDto.getFirstName();
        var user = new User();
        user.setFirstName(firstName);
        return user;
    }
}
