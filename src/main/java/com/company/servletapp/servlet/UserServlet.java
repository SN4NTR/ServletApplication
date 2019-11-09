package com.company.servletapp.servlet;

import com.company.servletapp.service.UserService;
import com.company.servletapp.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();

        String firstName = req.getParameter("firstName");
        String id = req.getParameter("id");

        if (firstName != null) {
            List<String> users = userService.getByFirstName(firstName);
            printWriter.print(users);
        } else if (id != null) {
            String user = userService.getById(id);
            printWriter.print(user);
        } else {
            List<String> users = userService.getAll();
            printWriter.print(users);
        }

        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.save(req.getReader());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.delete(req.getParameter("id"));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.update(req.getReader(), req.getParameter("id"));
    }
}
