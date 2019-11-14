package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;
import com.leverx.servletapp.util.ServletUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import static com.leverx.servletapp.user.mapper.UserMapper.convertCollectionToJson;
import static com.leverx.servletapp.user.mapper.UserMapper.convertJsonToUser;
import static com.leverx.servletapp.user.mapper.UserMapper.convertUserToJson;

@WebServlet(name = "UserServlet", urlPatterns = {"/users", "/users/*"})
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();

        StringBuffer url = req.getRequestURL();
        var id = ServletUtils.getIdFromUrl(url);
        String result;

        if (id == ServletUtils.ID_NOT_FOUND) {
            Collection<User> users = userService.findAll();
            result = convertCollectionToJson(users);
        } else {
            User user = userService.findById(id);
            result = convertUserToJson(user);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        printWriter.print(result);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        UserDto userDto = convertJsonToUser(reader);

        userService.save(userDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        StringBuffer url = req.getRequestURL();
        int id = ServletUtils.getIdFromUrl(url);

        if (id == ServletUtils.ID_NOT_FOUND) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            userService.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuffer url = req.getRequestURL();
        int id = ServletUtils.getIdFromUrl(url);

        if (id == ServletUtils.ID_NOT_FOUND) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            BufferedReader reader = req.getReader();
            UserDto userDto = convertJsonToUser(reader);
            userService.update(id, userDto);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
