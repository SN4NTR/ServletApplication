package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.cat.entity.dto.CatInputDto;
import com.leverx.servletapp.cat.entity.dto.CatWithIdsDto;
import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.user.entity.dto.UserInputDto;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.mapper.EntityMapper.collectionToJson;
import static com.leverx.servletapp.mapper.EntityMapper.entityToJson;
import static com.leverx.servletapp.mapper.EntityMapper.jsonToEntity;
import static com.leverx.servletapp.util.ServletUtils.getIdFromUrl;
import static com.leverx.servletapp.util.ServletUtils.getLastPartOfUrl;
import static com.leverx.servletapp.util.ServletUtils.getPenultimatePartOfUrl;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    private CatService catService = new CatServiceImpl();

    private static final String USERS_ENDPOINT = "users";
    private static final String CATS_ENDPOINT = "cats";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var valueOpt = getLastPartOfUrl(urlToString);
        var value = valueOpt.orElseThrow();
        var idToStringOpt = getPenultimatePartOfUrl(urlToString);
        var idToString = idToStringOpt.orElseThrow();

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
        var userDto = jsonToEntity(reader, UserInputDto.class);
        userService.save(userDto);
        resp.setStatus(SC_CREATED);
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
        var valueOpt = getLastPartOfUrl(urlToString);
        var value = valueOpt.orElseThrow();
        var idToStringOpt = getPenultimatePartOfUrl(urlToString);
        var idToString = idToStringOpt.orElseThrow();

        if (CATS_ENDPOINT.equals(value) && isParsable(idToString)) {
            assignCatToUser(req, resp, idToString);
        } else {
            updateUser(req, resp, urlToString);
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp, String urlToString) throws IOException {
        var idOpt = getIdFromUrl(urlToString);
        var id = idOpt.orElseThrow();
        var reader = req.getReader();
        var userInputDto = jsonToEntity(reader, UserInputDto.class);
        userService.update(id, userInputDto);
        resp.setStatus(SC_OK);
    }

    private void assignCatToUser(HttpServletRequest req, HttpServletResponse resp, String idToString) throws IOException {
        var id = parseInt(idToString);
        var reader = req.getReader();
        var catDtoWithIdsDto = jsonToEntity(reader, CatWithIdsDto.class);
        catService.assignCat(id, catDtoWithIdsDto);
        resp.setStatus(SC_OK);
    }

    private void printCatsByOwner(PrintWriter printWriter, String idToString, HttpServletResponse resp) {
        var id = parseInt(idToString);
        var cats = catService.findByOwnerId(id);
        if (!cats.isEmpty()) {
            var result = collectionToJson(cats);
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
        if (nonNull(user)) {
            var result = entityToJson(user);
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
