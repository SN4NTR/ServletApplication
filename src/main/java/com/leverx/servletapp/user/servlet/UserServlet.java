package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.converter.EntityConverter.collectionToJson;
import static com.leverx.servletapp.converter.EntityConverter.entityToJson;
import static com.leverx.servletapp.converter.EntityConverter.jsonToEntity;
import static com.leverx.servletapp.util.ServletUtils.getIdFromUrl;
import static com.leverx.servletapp.util.ServletUtils.getUserIdFormUrl;
import static com.leverx.servletapp.util.ServletUtils.getValueFromUrl;
import static com.leverx.servletapp.util.constant.UrlComponent.CATS_ENDPOINT;
import static com.leverx.servletapp.util.constant.UrlComponent.USERS_ENDPOINT;
import static java.lang.Integer.parseInt;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    private CatService catService = new CatServiceImpl();

    private static final int UNPROCESSABLE_ENTITY = 422;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();

        var idToStringOpt = getUserIdFormUrl(urlToString);
        var idToString = idToStringOpt.orElseThrow();

        var param = req.getParameter("firstName");
        var valueOpt = getValueFromUrl(urlToString, param);
        var value = valueOpt.orElseThrow();

        if (USERS_ENDPOINT.equals(value)) {
            printAllUsers(printWriter, resp);
        } else if (CATS_ENDPOINT.equals(value) && isParsable(idToString)) {
            printCatsByOwner(printWriter, idToString, resp);
        } else if (isParsable(value)) {
            printUserById(printWriter, value, resp);
        } else {
            printUserByFirstName(printWriter, value, resp);
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var reader = req.getReader();
            var userDto = jsonToEntity(reader, UserInputDto.class);
            userService.save(userDto);
            resp.setStatus(SC_CREATED);
        } catch (ValidationException | EntityNotFoundException ex) {
            resp.sendError(UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var idOpt = getIdFromUrl(urlToString);
        var id = idOpt.orElseThrow();
        userService.delete(id);
        resp.setStatus(SC_NO_CONTENT);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        updateUser(req, resp, urlToString);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp, String urlToString) throws IOException {
        try {
            var idOpt = getIdFromUrl(urlToString);
            var id = idOpt.orElseThrow();
            var reader = req.getReader();
            var userInputDto = jsonToEntity(reader, UserInputDto.class);
            userService.update(id, userInputDto);
            resp.setStatus(SC_OK);
        } catch (ValidationException | EntityNotFoundException ex) {
            resp.sendError(UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    private void printCatsByOwner(PrintWriter printWriter, String idToString, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(idToString);
            var cats = catService.findByOwnerId(id);
            var result = collectionToJson(cats);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } catch (EntityNotFoundException ex) {
            resp.sendError(UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    private void printUserByFirstName(PrintWriter printWriter, String value, HttpServletResponse resp) {
        var users = userService.findByName(value);
        if (isNotEmpty(users)) {
            var result = collectionToJson(users);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } else {
            resp.setStatus(UNPROCESSABLE_ENTITY);
        }
    }

    private void printUserById(PrintWriter printWriter, String value, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(value);
            var user = userService.findById(id);
            var result = entityToJson(user);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } catch (EntityNotFoundException ex) {
            resp.sendError(UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    private void printAllUsers(PrintWriter printWriter, HttpServletResponse resp) {
        var users = userService.findAll();
        var result = collectionToJson(users);
        printWriter.print(result);
        resp.setStatus(SC_OK);
    }
}
