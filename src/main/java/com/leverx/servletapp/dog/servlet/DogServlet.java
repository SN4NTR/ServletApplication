package com.leverx.servletapp.dog.servlet;

import com.leverx.servletapp.dog.dto.DogInputDto;
import com.leverx.servletapp.dog.service.DogService;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.constant.HttpResponseStatus.CREATED;
import static com.leverx.servletapp.constant.HttpResponseStatus.OK;
import static com.leverx.servletapp.factory.BeanFactory.getDogService;
import static com.leverx.servletapp.converter.EntityConverter.collectionToJson;
import static com.leverx.servletapp.converter.EntityConverter.entityToJson;
import static com.leverx.servletapp.converter.EntityConverter.jsonToEntity;
import static com.leverx.servletapp.util.ServletUtils.getLastPartOfUrl;
import static com.leverx.servletapp.util.constant.UrlComponent.DOGS_ENDPOINT;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class DogServlet extends HttpServlet {

    private DogService dogService;

    public DogServlet() {
        dogService = getDogService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var valueOpt = getLastPartOfUrl(urlToString);
        var value = valueOpt.orElseThrow();

        if (DOGS_ENDPOINT.equals(value)) {
            printAllDogs(printWriter, resp);
        } else if (isParsable(value)) {
            printDogById(printWriter, value, resp);
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var reader = req.getReader();
            var dogDto = jsonToEntity(reader, DogInputDto.class);
            dogService.save(dogDto);
            resp.setStatus(CREATED);
        } catch (ValidationException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }

    private void printAllDogs(PrintWriter printWriter, HttpServletResponse resp) {
        var dogs = dogService.findAll();
        var result = collectionToJson(dogs);
        printWriter.print(result);
        resp.setStatus(OK);
    }

    private void printDogById(PrintWriter printWriter, String value, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(value);
            var dog = dogService.findById(id);
            var result = entityToJson(dog);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }
}
