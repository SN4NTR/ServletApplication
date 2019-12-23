package com.leverx.servletapp.web;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HttpResponseStatus {

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NO_CONTENT = 204;
    public static final int NOT_FOUND = 404;
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int INTERNAL_SERVER_ERROR = 500;
}
