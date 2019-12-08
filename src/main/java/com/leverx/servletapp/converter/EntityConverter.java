package com.leverx.servletapp.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.servletapp.exception.InternalServerErrorException;

import java.io.BufferedReader;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class EntityConverter {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static String readJsonBody(BufferedReader reader) {
        return reader.lines()
                .collect(joining());
    }

    public static <T> T jsonToEntity(BufferedReader reader, Class<T> tClass) {
        try {
            var jsonBody = readJsonBody(reader);
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
                .map(EntityConverter::entityToJson)
                .collect(joining("\n"));
    }
}
