package com.leverx.servletapp.user.servlet;

import com.google.gson.Gson;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.repository.SQL;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    private final static Gson GSON = new Gson();

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();

        String firstName = req.getParameter("firstName");
        String id = req.getParameter("id");

        if (firstName != null) {
            Collection<User> users = userService.findByFirstName(firstName);
            String result = GSON.toJson(users);
            printWriter.print(result);
        } else if (id != null) {
            User user = userService.findById(Integer.parseInt(id));
            String result = GSON.toJson(user);
            printWriter.print(result);
        } else {
            Collection<User> users = userService.findAll();
            String result = GSON.toJson(users);
            printWriter.print(result);
        }

        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        User user = GSON.fromJson(reader, User.class);

        userService.save(user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter(SQL.ID));

        userService.delete(id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        int id = Integer.parseInt(req.getParameter(SQL.ID));

        User user = GSON.fromJson(reader, User.class);
        user.setId(id);

        userService.update(user);
    }
}
