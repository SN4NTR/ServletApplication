package com.leverx.servletapp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletUtils {

    private static final String DELIMITER = "/";

    public static String getPenultimatePartOfUrl(String url) {
        var urlComponents = url.split(DELIMITER);
        var penultimateElementIndex = urlComponents.length - 2;
        return urlComponents[penultimateElementIndex];
    }

    public static String getLastPartOFUrl(String url) {
        var urlComponents = url.split(DELIMITER);
        var lastElementIndex = urlComponents.length - 1;
        return urlComponents[lastElementIndex];
    }

    public static Optional<Integer> getIdFromUrl(String url) {
        var value = getLastPartOFUrl(url);
        return isParsable(value) ? Optional.of(Integer.parseInt(value)) : Optional.empty();
    }
}
