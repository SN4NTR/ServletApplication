package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.user.mapper.UserMapper.collectionToJson;
import static com.leverx.servletapp.user.mapper.UserMapper.jsonToUserDto;
import static com.leverx.servletapp.user.mapper.UserMapper.readJsonBody;
import static com.leverx.servletapp.user.mapper.UserMapper.userToJson;
import static com.leverx.servletapp.util.ServletUtils.getIdFromUrl;
import static com.leverx.servletapp.util.ServletUtils.getValueFromUrl;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    private static final String PATH = "users";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var value = getValueFromUrl(urlToString);

        if (PATH.equals(value)) {
            printAllUsers(printWriter, resp);
        } else if (isParsable(value)) {
            printUserById(printWriter, value, resp);
        } else {
            printUserByFirstName(printWriter, value, resp);
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var reader = req.getReader();
        var jsonBody = readJsonBody(reader);
        var userDto = jsonToUserDto(jsonBody);
        userService.save(userDto);
        resp.setStatus(SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var idOpt = getIdFromUrl(urlToString);

        if (idOpt.isPresent()) {
            var id = idOpt.get();
            userService.delete(id);
            resp.setStatus(SC_NO_CONTENT);
        } else {
            resp.sendError(SC_BAD_REQUEST, "User can't be found");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var idOpt = getIdFromUrl(urlToString);
        if (idOpt.isPresent()) {
            var id = idOpt.get();
            var reader = req.getReader();
            var jsonBody = readJsonBody(reader);
            var userDto = jsonToUserDto(jsonBody);
            userService.update(id, userDto);
            resp.setStatus(SC_OK);
        } else {
            resp.sendError(SC_BAD_REQUEST, "User can't be found");
        }
    }

    private void printUserByFirstName(PrintWriter printWriter, String value, HttpServletResponse resp) {
        var users = userService.findByName(value);
        if (!users.isEmpty()) {
            var result = collectionToJson(users);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } else {
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    private void printUserById(PrintWriter printWriter, String value, HttpServletResponse resp) {
        var id = Integer.parseInt(value);
        var user = userService.findById(id);
        if (user != null) {
            var result = userToJson(user);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } else {
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    private void printAllUsers(PrintWriter printWriter, HttpServletResponse resp) {
        var users = userService.findAll();
        var result = collectionToJson(users);
        printWriter.print(result);
        resp.setStatus(SC_OK);
    }
}
