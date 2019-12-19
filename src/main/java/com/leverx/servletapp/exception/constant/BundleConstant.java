package com.leverx.servletapp.exception.constant;

import static java.util.ResourceBundle.getBundle;

public class BundleConstant {

    private static final String BUNDLE_NAME = "message";

    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String CAT_NOT_FOUND = "cat_not_found";
    public static final String DOG_NOT_FOUND = "dog_not_found";

    public static String getLocalizedMessage(String key) {
        var resourceBundle = getBundle(BUNDLE_NAME);
        return resourceBundle.getString(key);
    }
}
