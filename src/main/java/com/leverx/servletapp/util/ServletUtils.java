package com.leverx.servletapp.user.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserServletUtils {

    public static final int ID_NOT_FOUND = -1;

    private static final String delimiter = "/";

    public static int getIdFromUrl(StringBuffer url) {
        String urlToString = url.toString();
        String[] urlComponents = urlToString.split(delimiter);

        var index = urlComponents.length - 1;
        String lastElement = urlComponents[index];
        if (isNumeric(lastElement)) {
            return Integer.parseInt(lastElement);
        } else {
            return ID_NOT_FOUND;
        }
    }

    private static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
