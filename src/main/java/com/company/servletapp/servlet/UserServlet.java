package com.company.servletapp.servlet;

import com.company.servletapp.entity.User;
import com.company.servletapp.service.UserService;
import com.company.servletapp.service.UserServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    private final static Gson gson = new Gson();

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.getAll();

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<String> usersToString = new ArrayList<>();
        for (User u: users) {
            usersToString.add(gson.toJson(u));
        }

        out.print(usersToString);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = gson.fromJson(req.getReader(), User.class);
        userService.save(user);
    }
}
