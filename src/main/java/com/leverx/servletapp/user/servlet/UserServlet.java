package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.cat.entity.CatDto;
import com.leverx.servletapp.user.entity.UserDto;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.cat.mapper.CatMapper.catsWithoutOwners;
import static com.leverx.servletapp.mapper.EntityMapper.collectionToJson;
import static com.leverx.servletapp.mapper.EntityMapper.entityToJson;
import static com.leverx.servletapp.mapper.EntityMapper.jsonToEntity;
import static com.leverx.servletapp.mapper.EntityMapper.readJsonBody;
import static com.leverx.servletapp.user.mapper.UserMapper.usersWithoutCats;
import static com.leverx.servletapp.util.ServletUtils.getIdFromUrl;
import static com.leverx.servletapp.util.ServletUtils.getLastPartOFUrl;
import static com.leverx.servletapp.util.ServletUtils.getPenultimatePartOfUrl;
import static java.lang.Integer.parseInt;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    private static final String USERS_ENDPOINT = "users";
    private static final String CATS_ENDPOINT = "cats";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var value = getLastPartOFUrl(urlToString);
        var idToString = getPenultimatePartOfUrl(urlToString);

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
        var reader = req.getReader();
        var jsonBody = readJsonBody(reader);
        var userDto = jsonToEntity(jsonBody, UserDto.class);
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
        var value = getLastPartOFUrl(urlToString);
        var idToString = getPenultimatePartOfUrl(urlToString);

        var reader = req.getReader();
        var jsonBody = readJsonBody(reader);

        if (CATS_ENDPOINT.equals(value) && isParsable(idToString)) {
            assignCatToUser(resp, idToString, jsonBody);
        } else {
            updateUser(resp, urlToString, jsonBody);
        }
    }

    private void updateUser(HttpServletResponse resp, String urlToString, String jsonBody) throws IOException {
        var idOpt = getIdFromUrl(urlToString);
        if (idOpt.isPresent()) {
            var id = idOpt.get();
            var userDto = jsonToEntity(jsonBody, UserDto.class);
            userService.update(id, userDto);
            resp.setStatus(SC_OK);
        } else {
            resp.sendError(SC_BAD_REQUEST, "User can't be found");
        }
    }

    private void assignCatToUser(HttpServletResponse resp, String idToString, String jsonBody) {
        var catDto = jsonToEntity(jsonBody, CatDto.class);
        var id = parseInt(idToString);
        userService.assignCat(id, catDto);
        resp.setStatus(SC_OK);
    }

    private void printCatsByOwner(PrintWriter printWriter, String idToString, HttpServletResponse resp) {
        var id = parseInt(idToString);
        var cats = userService.findCatsByUserId(id);
        if (cats != null) {
            var catsWithoutOwners = catsWithoutOwners(cats);
            var result = collectionToJson(catsWithoutOwners);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } else {
            resp.setStatus(SC_NOT_FOUND);
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
        var id = parseInt(value);
        var user = userService.findById(id);
        if (user != null) {
            var result = entityToJson(user);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } else {
            resp.setStatus(SC_NOT_FOUND);
        }
    }

    private void printAllUsers(PrintWriter printWriter, HttpServletResponse resp) {
        var users = userService.findAll();
        var usersWithoutCats = usersWithoutCats(users);
        var result = collectionToJson(usersWithoutCats);
        printWriter.print(result);
        resp.setStatus(SC_OK);
    }
}
