package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.web.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.leverx.servletapp.user.servlet.MethodType.GET_ALL_USERS;
import static com.leverx.servletapp.user.servlet.MethodType.GET_USERS_ANIMALS;
import static com.leverx.servletapp.user.servlet.MethodType.GET_USERS_CATS;
import static com.leverx.servletapp.user.servlet.MethodType.GET_USERS_DOGS;
import static com.leverx.servletapp.user.servlet.MethodType.GET_USER_BY_ATTRIBUTE;
import static com.leverx.servletapp.web.UrlPath.ANIMALS_ENDPOINT;
import static com.leverx.servletapp.web.UrlPath.CATS_ENDPOINT;
import static com.leverx.servletapp.web.UrlPath.DOGS_ENDPOINT;
import static com.leverx.servletapp.web.UrlPath.USERS_ENDPOINT;
import static java.util.Objects.isNull;
import static java.util.regex.Pattern.compile;

class UserServletUtil extends ServletUtils {

    static final String TRANSFER_ACTION = "transfer";

    private static final int FIND_ALL_GROUP = 3;
    private static final int FIND_BY_ID_GROUP = 4;
    private static final int FIND_BY_OWNER_GROUP = 5;
    private static final String FIRST_NAME_PARAMETER = "firstName";

    static MethodType getMethodType(HttpServletRequest req) {
        var valueOpt = determineRequestType(req);
        var value = valueOpt.orElseThrow();
        return switch (value) {
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

    private static Optional<String> determineRequestType(HttpServletRequest req) {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var value = findByOwner(urlToString);
        if (value.isPresent()) {
            return value;
        }

        value = findById(urlToString);
        if (value.isPresent()) {
            return value;
        }

        var parameter = req.getParameter(FIRST_NAME_PARAMETER);
        value = findByFirstName(urlToString, parameter);
        return value;
    }

    private static Optional<String> findByFirstName(String url, String parameter) {
        var findAllPattern = compile("(.+)//(.+)/(\\w+)/?");
        var findAllMatcher = findAllPattern.matcher(url);

        if (findAllMatcher.matches()) {
            var value = findAllMatcher.group(FIND_ALL_GROUP);
            return isNull(parameter) ? Optional.of(value) : Optional.of(parameter);
        }
        return Optional.empty();
    }

    private static Optional<String> findById(String url) {
        var findByIdPattern = compile("(.+)//(.+)/(\\w+)/(\\d+)");
        var findByIdMatcher = findByIdPattern.matcher(url);

        if (findByIdMatcher.matches()) {
            var id = findByIdMatcher.group(FIND_BY_ID_GROUP);
            return Optional.of(id);
        }
        return Optional.empty();
    }

    private static Optional<String> findByOwner(String url) {
        var findByOwnerPattern = compile("(.+)//(.+)/(\\w+)/(\\d+)/(\\w+)");
        var findByOwnerMatcher = findByOwnerPattern.matcher(url);

        if (findByOwnerMatcher.matches()) {
            var value = findByOwnerMatcher.group(FIND_BY_OWNER_GROUP);
            return Optional.of(value);
        }
        return Optional.empty();
    }
}
