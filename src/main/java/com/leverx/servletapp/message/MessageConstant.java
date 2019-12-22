package com.leverx.servletapp.message;

import static java.util.ResourceBundle.getBundle;

public class MessageConstant {

    public static final String MESSAGE_BUNDLE_NAME = "message";
    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String CAT_NOT_FOUND = "cat_not_found";
    public static final String DOG_NOT_FOUND = "dog_not_found";
    public static final String TO_JSON_ERROR = "to_json_error";
    public static final String FROM_JSON_ERROR = "from_json_error";

    public static String getLocalizedMessage(String key) {
        var resourceBundle = getBundle(MESSAGE_BUNDLE_NAME);
        return resourceBundle.getString(key);
    }
}
