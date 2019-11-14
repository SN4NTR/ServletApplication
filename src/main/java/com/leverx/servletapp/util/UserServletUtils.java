package com.leverx.servletapp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserServletUtils {

    private static final String DELIMITER = "/";

    public static final String PATH = "users";

    public static String getValueFromUrl(String url) {
        var urlComponents = url.split(DELIMITER);
        var lastElementIndex = urlComponents.length - 1;

        return urlComponents[lastElementIndex];
    }

    public static Optional<Integer> getIdFromUrl(String url) {
        String value = getValueFromUrl(url);

        return isParsable(value) ? Optional.of(Integer.parseInt(value)) : Optional.empty();
    }
}
