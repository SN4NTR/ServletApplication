package com.leverx.servletapp.web.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.leverx.servletapp.web.UrlPath.DELIMITER;
import static com.leverx.servletapp.web.UrlPath.LAST_ELEMENT_POSITION;
import static com.leverx.servletapp.web.UrlPath.PENULTIMATE_ELEMENT_POSITION;
import static java.util.Objects.isNull;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletUtils {

    private static final int FIND_ALL_GROUP = 3;
    private static final int FIND_BY_ID_GROUP = 4;
    private static final int FIND_BY_OWNER_GROUP = 5;
    private static final String FIRST_NAME_PARAMETER = "firstName";

    public static Optional<String> getUserIdFromUrl(String url) {
        return getUrlPartByPosition(url, PENULTIMATE_ELEMENT_POSITION);
    }

    public static Optional<String> getLastPartOfUrl(String url) {
        return getUrlPartByPosition(url, LAST_ELEMENT_POSITION);
    }

    public static Optional<Integer> getIdFromUrl(String url) {
        var valueOpt = getLastPartOfUrl(url);
        var value = valueOpt.orElseThrow();
        return isParsable(value) ? Optional.of(Integer.parseInt(value)) : Optional.empty();
    }

    public static Optional<String> defineGetMethodFromRequest(HttpServletRequest req) {
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

    private static Optional<String> getUrlPartByPosition(String url, int positionFromEnd) {
        var urlParts = url.split(DELIMITER);
        if (isNotEmpty(urlParts)) {
            var elementIndex = urlParts.length - positionFromEnd;
            return Optional.of(urlParts[elementIndex]);
        } else {
            return Optional.empty();
        }
    }
}
