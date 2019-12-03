package com.leverx.servletapp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.InternalServerErrorException;
import java.io.BufferedReader;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class EntityMapper {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String readJsonBody(BufferedReader reader) {
        return reader.lines()
                .collect(joining());
    }

    public static <T> T jsonToEntity(String jsonBody, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(jsonBody, tClass);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public static <T> String entityToJson(T t) {
        try {
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public static <T> String collectionToJson(Collection<T> t) {
        return t.stream()
                .map(EntityMapper::entityToJson)
                .collect(joining("\n"));
    }
}
