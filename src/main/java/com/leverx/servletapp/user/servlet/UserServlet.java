package com.leverx.servletapp.user.servlet;

import com.leverx.servletapp.animal.service.AnimalService;
import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.core.exception.EntityNotFoundException;
import com.leverx.servletapp.core.exception.ValidationException;
import com.leverx.servletapp.dog.service.DogService;
import com.leverx.servletapp.user.dto.UserInputDto;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.web.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.core.converter.EntityConverter.collectionToJson;
import static com.leverx.servletapp.core.converter.EntityConverter.entityToJson;
import static com.leverx.servletapp.core.converter.EntityConverter.jsonToEntity;
import static com.leverx.servletapp.core.factory.BeanFactory.getAnimalService;
import static com.leverx.servletapp.core.factory.BeanFactory.getCatService;
import static com.leverx.servletapp.core.factory.BeanFactory.getDogService;
import static com.leverx.servletapp.core.factory.BeanFactory.getUserService;
import static com.leverx.servletapp.user.servlet.UserServletUtil.getMethodType;
import static com.leverx.servletapp.web.HttpResponseStatus.CREATED;
import static com.leverx.servletapp.web.HttpResponseStatus.NO_CONTENT;
import static com.leverx.servletapp.web.HttpResponseStatus.OK;
import static com.leverx.servletapp.web.util.ServletUtils.getIdFromUrl;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class UserServlet extends HttpServlet {

    private UserService userService;
    private AnimalService animalService;
    private CatService catService;
    private DogService dogService;

    public UserServlet() {
        userService = getUserService();
        animalService = getAnimalService();
        catService = getCatService();
        dogService = getDogService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var methodType = getMethodType(req);

        switch (methodType) {
            case GET_ALL_USERS -> printAllUsers(printWriter, resp);
            case GET_USERS_CATS -> printCatsByOwner(printWriter, urlToString, resp);
            case GET_USERS_DOGS -> printDogsByOwner(printWriter, urlToString, resp);
            case GET_USERS_ANIMALS -> printAnimalsByOwner(printWriter, urlToString, resp);
            default -> printUserByAttribute(printWriter, urlToString, resp);
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var url = req.getRequestURL();
        var urlToString = url.toString();
        var idOpt = getIdFromUrl(urlToString);
        var id = idOpt.orElseThrow();

        try {
            var reader = req.getReader();
            var userDto = jsonToEntity(reader, UserInputDto.class);
            userService.save(userDto);
            resp.setStatus(CREATED);
        } catch (ValidationException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var url = req.getRequestURL();
            var urlToString = url.toString();
            var idOpt = getIdFromUrl(urlToString);
            var id = idOpt.orElseThrow();
            userService.delete(id);
            resp.setStatus(NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
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
            resp.setStatus(OK);
        } catch (ValidationException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    private void printCatsByOwner(PrintWriter printWriter, String url, HttpServletResponse resp) throws IOException {
        try {
            var idOpt = getIdFromUrl(url);
            var id = idOpt.orElseThrow();
            var cats = catService.findByOwnerId(id);
            var result = collectionToJson(cats);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    private void printDogsByOwner(PrintWriter printWriter, String url, HttpServletResponse resp) throws IOException {
        try {
            var idOpt = getIdFromUrl(url);
            var id = idOpt.orElseThrow();
            var dogs = dogService.findByOwnerId(id);
            var result = collectionToJson(dogs);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    private void printAnimalsByOwner(PrintWriter printWriter, String url, HttpServletResponse resp) throws IOException {
        try {
            var idOpt = getIdFromUrl(url);
            var id = idOpt.orElseThrow();
            var animals = animalService.findByOwnerId(id);
            var result = collectionToJson(animals);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    private void printUserByAttribute(PrintWriter printWriter, String url, HttpServletResponse resp) throws IOException {
        var valueOpt = ServletUtils.getLastPartOfUrl(url);
        var value = valueOpt.orElseThrow();
        if (isParsable(value)) {
            printUserById(printWriter, value, resp);
        } else {
            printUserByFirstName(printWriter, value, resp);
        }
    }

    private void printUserByFirstName(PrintWriter printWriter, String value, HttpServletResponse resp) {
        var users = userService.findByName(value);
        var result = collectionToJson(users);
        printWriter.print(result);
        resp.setStatus(OK);
    }

    private void printUserById(PrintWriter printWriter, String value, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(value);
            var user = userService.findById(id);
            var result = entityToJson(user);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    private void printAllUsers(PrintWriter printWriter, HttpServletResponse resp) {
        var users = userService.findAll();
        var result = collectionToJson(users);
        printWriter.print(result);
        resp.setStatus(OK);
    }
}
