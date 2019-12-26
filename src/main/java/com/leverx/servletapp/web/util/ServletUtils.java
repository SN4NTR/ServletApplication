package com.leverx.servletapp.web.util;

import java.util.Optional;

import static com.leverx.servletapp.web.UrlPath.DELIMITER;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

public class ServletUtils {

    private static final String delimiter = "/";
    private static final int idPositionInUrl = 4;

    public static Optional<String> getLastPartOfUrl(String url) {
        var urlParts = url.split(DELIMITER);
        if (isNotEmpty(urlParts)) {
            var elementIndex = urlParts.length - 1;
            var element = urlParts[elementIndex];
            return Optional.of(element);
        }
        return Optional.empty();
    }

    public static Optional<Integer> getIdFromUrl(String url) {
        var urlParts = url.split(delimiter);
        if (isNotEmpty(urlParts)) {
            var idToString = urlParts[idPositionInUrl];
            var id = parseInt(idToString);
            return Optional.of(id);
        }
        return Optional.empty();
    }
}
