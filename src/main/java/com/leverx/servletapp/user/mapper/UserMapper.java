package com.leverx.servletapp.user.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.ws.rs.InternalServerErrorException;
import java.io.BufferedReader;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String readJsonBody(BufferedReader reader) {
        return reader.lines()
                .collect(joining());
    }

    public static UserDto jsonToUserDto(String jsonBody) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonBody, UserDto.class);
    }

    public static String userToJson(User user) {
        try {
            return OBJECT_MAPPER.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        }
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
