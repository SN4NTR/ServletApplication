package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.leverx.servletapp.user.mapper.UserMapper.convertCollectionToJson;
import static com.leverx.servletapp.user.mapper.UserMapper.convertJsonToUserDto;
import static com.leverx.servletapp.user.mapper.UserMapper.convertUserToJson;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.PATH;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.getIdFromUrl;
import static com.leverx.servletapp.user.servlet.util.UserServletUtils.getValueFromUrl;
import static com.leverx.servletapp.user.validator.UserValidator.isFirstNameLengthValid;
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
            var users = userService.findAll();
            var result = convertCollectionToJson(users);
            printWriter.print(result);
        } else if (isParsable(value)) {
            var id = Integer.parseInt(value);
            var user = userService.findById(id);
            var result = convertUserToJson(user);
            printWriter.print(result);
        } else {
            var users = userService.findByName(value);
            var result = convertCollectionToJson(users);
            printWriter.print(result);
        }

        resp.setStatus(SC_OK);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var isCorrect = isValidPostRequest(req);
        resp.setStatus(isCorrect ? SC_CREATED : SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        var url = req.getRequestURL();
        var urlToString = url.toString();

        var isCorrect = isValidDeleteRequest(urlToString);
        resp.setStatus(isCorrect ? SC_NO_CONTENT : SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();

        var isCorrect = isValidPutRequest(req, urlToString);
        resp.setStatus(isCorrect ? SC_OK : SC_BAD_REQUEST);
    }

    private boolean isValidPostRequest(HttpServletRequest req) throws IOException {
        var reader = req.getReader();
        var userDto = convertJsonToUserDto(reader);
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
        var userDto = convertJsonToUserDto(reader);
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
