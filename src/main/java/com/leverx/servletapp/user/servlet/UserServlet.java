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
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.PATH;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.getIdFromUrl;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.getValueFromUrl;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var value = getValueFromUrl(urlToString);

        if (PATH.equals(value)) {
            printAllUsers(printWriter);
        } else if (isParsable(value)) {
            printUserById(printWriter, value);
        } else {
            printUserByFirstName(printWriter, value);
        }

        resp.setStatus(SC_OK);
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

    private void printUserByFirstName(PrintWriter printWriter, String value) {
        var users = userService.findByName(value);
        var result = collectionToJson(users);
        printWriter.print(result);
    }

    private void printUserById(PrintWriter printWriter, String value) {
        var id = Integer.parseInt(value);
        var user = userService.findById(id);
        var result = userToJson(user);
        printWriter.print(result);
    }

    private void printAllUsers(PrintWriter printWriter) {
        var users = userService.findAll();
        var result = collectionToJson(users);
        printWriter.print(result);
    }
}
