package com.leverx.servletapp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletUtils {

    private static final String DELIMITER = "/";

    public static Optional<String> getPenultimatePartOfUrl(String url) {
        var urlComponents = url.split(DELIMITER);
        var penultimateElementIndex = urlComponents.length - 2;
        return Optional.of(urlComponents[penultimateElementIndex]);
    }

    public static Optional<String> getLastPartOfUrl(String url) {
        var urlComponents = url.split(DELIMITER);
        var lastElementIndex = urlComponents.length - 1;
        return Optional.of(urlComponents[lastElementIndex]);
    }

    public static Optional<Integer> getIdFromUrl(String url) {
        var valueOpt = getLastPartOfUrl(url);
        var value = valueOpt.orElseThrow();
        return isParsable(value) ? Optional.of(Integer.parseInt(value)) : Optional.empty();
    }
}
