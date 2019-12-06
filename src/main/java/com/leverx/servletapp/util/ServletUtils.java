package com.leverx.servletapp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static com.leverx.servletapp.util.constant.UrlComponent.DELIMITER;
import static com.leverx.servletapp.util.constant.UrlComponent.LAST_ELEMENT_POSITION;
import static com.leverx.servletapp.util.constant.UrlComponent.PENULTIMATE_ELEMENT_POSITION;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletUtils {

    public static Optional<String> getPenultimatePartOfUrl(String url) {
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
