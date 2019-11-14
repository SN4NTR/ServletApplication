package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.leverx.servletapp.user.mapper.UserMapper.convertCollectionToJson;
import static com.leverx.servletapp.user.mapper.UserMapper.convertJsonToUserDto;
import static com.leverx.servletapp.user.mapper.UserMapper.convertUserToJson;
import static com.leverx.servletapp.user.validator.UserValidator.isFirstNameLengthValid;
import static com.leverx.servletapp.util.UserServletUtils.PATH;
import static com.leverx.servletapp.util.UserServletUtils.getIdFromUrl;
import static com.leverx.servletapp.util.UserServletUtils.getValueFromUrl;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@WebServlet(name = "UserServlet", urlPatterns = {"/users", "/users/*"})
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var value = getValueFromUrl(urlToString);

        if (PATH.equals(value)) {
            Collection<User> users = userService.findAll();
            String result = convertCollectionToJson(users);
            printWriter.print(result);
        } else if (isParsable(value)) {
            int id = Integer.parseInt(value);
            User user = userService.findById(id);
            String result = convertUserToJson(user);
            printWriter.print(result);
        } else {
            Collection<User> users = userService.findByName(value);
            String result = convertCollectionToJson(users);
            printWriter.print(result);
        }

        resp.setStatus(SC_OK);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var reader = req.getReader();
        var userDto = convertJsonToUserDto(reader);
        var userFirstName = userDto.getFirstName();

        if (isFirstNameLengthValid(userFirstName)) {
            userService.save(userDto);
            resp.setStatus(SC_CREATED);
        } else {
            resp.setStatus(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var idOptional = getIdFromUrl(urlToString);

        if (idOptional.isPresent()) {
            var id = idOptional.get();
            userService.delete(id);
            resp.setStatus(SC_NO_CONTENT);
        } else {
            resp.setStatus(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var idOptional = getIdFromUrl(urlToString);

        if (idOptional.isPresent()) {
            var reader = req.getReader();
            var userDto = convertJsonToUserDto(reader);
            var userFirstName = userDto.getFirstName();

            if (isFirstNameLengthValid(userFirstName)) {
                int id = idOptional.get();
                userService.update(id, userDto);
                resp.setStatus(SC_CREATED);
            } else {
                resp.setStatus(SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(SC_BAD_REQUEST);
        }
    }
}
