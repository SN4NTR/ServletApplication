package com.leverx.servletapp.user.servlet.util;

import com.leverx.servletapp.user.servlet.util.constant.MethodType;

import static com.leverx.servletapp.user.servlet.util.constant.MethodType.GET_ALL_USERS;
import static com.leverx.servletapp.user.servlet.util.constant.MethodType.GET_USERS_ANIMALS;
import static com.leverx.servletapp.user.servlet.util.constant.MethodType.GET_USERS_CATS;
import static com.leverx.servletapp.user.servlet.util.constant.MethodType.GET_USERS_DOGS;
import static com.leverx.servletapp.user.servlet.util.constant.MethodType.GET_USER_BY_ATTRIBUTE;
import static com.leverx.servletapp.util.constant.UrlComponent.ANIMALS_ENDPOINT;
import static com.leverx.servletapp.util.constant.UrlComponent.CATS_ENDPOINT;
import static com.leverx.servletapp.util.constant.UrlComponent.DOGS_ENDPOINT;
import static com.leverx.servletapp.util.constant.UrlComponent.USERS_ENDPOINT;

public class UserServletUtil {

    public static MethodType getMethodType(String urlValue) {
        return switch (urlValue) {
            case USERS_ENDPOINT:
                yield GET_ALL_USERS;
            case CATS_ENDPOINT:
                yield GET_USERS_CATS;
            case DOGS_ENDPOINT:
                yield GET_USERS_DOGS;
            case ANIMALS_ENDPOINT:
                yield GET_USERS_ANIMALS;
            default:
                yield GET_USER_BY_ATTRIBUTE;
        };
    }
}
