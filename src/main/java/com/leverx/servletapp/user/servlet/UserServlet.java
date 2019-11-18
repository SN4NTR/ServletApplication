package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.leverx.servletapp.user.mapper.UserMapper.collectionToJson;
import static com.leverx.servletapp.user.mapper.UserMapper.jsonToUserDto;
import static com.leverx.servletapp.user.mapper.UserMapper.userToJson;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.PATH;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.getIdFromUrl;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.getValueFromUrl;
import static com.leverx.servletapp.user.validator.UserValidator.isFirstNameLengthValid;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    private static final String ERROR_MESSAGE = "Invalid request parameters";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var value = getValueFromUrl(urlToString);

        if (PATH.equals(value)) {
            var users = userService.findAll();
            var result = collectionToJson(users);
            printWriter.print(result);
        } else if (isParsable(value)) {
            var id = Integer.parseInt(value);
            var user = userService.findById(id);
            var result = userToJson(user);
            printWriter.print(result);
        } else {
            var users = userService.findByName(value);
            var result = collectionToJson(users);
            printWriter.print(result);
        }

        resp.setStatus(SC_OK);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var isCorrect = isValidPostRequest(req);

        if (isCorrect) {
            resp.setStatus(SC_CREATED);
        } else {
            resp.sendError(SC_BAD_REQUEST, ERROR_MESSAGE);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();

        var isCorrect = isValidDeleteRequest(urlToString);

        if (isCorrect) {
            resp.setStatus(SC_NO_CONTENT);
        } else {
            resp.sendError(SC_BAD_REQUEST, ERROR_MESSAGE);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();

        var isCorrect = isValidPutRequest(req, urlToString);
        if (isCorrect) {
            resp.setStatus(SC_OK);
        } else {
            resp.sendError(SC_BAD_REQUEST, ERROR_MESSAGE);
        }
    }

    private boolean isValidPostRequest(HttpServletRequest req) throws IOException {
        var reader = req.getReader();
        var userDto = jsonToUserDto(reader);
        var firstName = userDto.getFirstName();

        if (isFirstNameLengthValid(firstName)) {
            userService.save(userDto);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidDeleteRequest(String url) {
        var idOptional = getIdFromUrl(url);

        if (idOptional.isPresent()) {
            var id = idOptional.get();
            userService.delete(id);
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPutRequest(HttpServletRequest req, String url) throws IOException {
        var reader = req.getReader();
        var userDto = jsonToUserDto(reader);
        var firstName = userDto.getFirstName();
        var idOptional = getIdFromUrl(url);

        if (idOptional.isPresent() && isFirstNameLengthValid(firstName)) {
            var id = idOptional.get();
            userService.update(id, userDto);
            return true;
        } else {
            return false;
        }
    }
}
