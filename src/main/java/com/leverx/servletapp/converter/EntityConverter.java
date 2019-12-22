package com.leverx.servletapp.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.servletapp.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.util.Collection;

import static com.leverx.servletapp.constant.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static com.leverx.servletapp.message.MessageConstant.FROM_JSON_ERROR;
import static com.leverx.servletapp.message.MessageConstant.MESSAGE_BUNDLE_NAME;
import static com.leverx.servletapp.message.MessageConstant.TO_JSON_ERROR;
import static com.leverx.servletapp.message.MessageConstant.getLocalizedMessage;
import static java.util.stream.Collectors.joining;

@Slf4j
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
            var message = getLocalizedMessage(MESSAGE_BUNDLE_NAME, FROM_JSON_ERROR);
            log.error(message);
            throw new InternalServerErrorException(message, INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> String entityToJson(T t) {
        try {
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            var message =getLocalizedMessage(MESSAGE_BUNDLE_NAME, TO_JSON_ERROR);
            log.error(message);
            throw new InternalServerErrorException(message, INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> String collectionToJson(Collection<T> t) {
        return t.stream()
                .map(EntityConverter::entityToJson)
                .collect(joining("\n"));
    }
}
