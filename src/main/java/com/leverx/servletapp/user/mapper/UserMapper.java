package com.leverx.servletapp.user.mapper;

import com.google.gson.Gson;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Reader;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    private final static Gson GSON = new Gson();

    public static String convertUserToJson(User user) {
        return GSON.toJson(user);
    }

    public static String convertCollectionToJson(Collection<User> users) {
        return users.stream()
                .map(GSON::toJson)
                .toString();
    }

    public static UserDto convertJsonToUser(Reader reader) {
        return GSON.fromJson(reader, UserDto.class);
    }
}
