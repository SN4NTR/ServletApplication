package com.leverx.servletapp.animal.servlet;

import com.leverx.servletapp.animal.service.AnimalService;
import com.leverx.servletapp.core.exception.EntityNotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.web.HttpResponseStatus.OK;
import static com.leverx.servletapp.core.converter.EntityConverter.collectionToJson;
import static com.leverx.servletapp.core.converter.EntityConverter.entityToJson;
import static com.leverx.servletapp.core.factory.BeanFactory.getAnimalService;
import static com.leverx.servletapp.web.util.ServletUtils.getLastPartOfUrl;
import static com.leverx.servletapp.web.UrlPath.ANIMALS_ENDPOINT;
import static java.lang.Integer.parseInt;

public class AnimalServlet extends HttpServlet {

    private AnimalService animalService;

    public AnimalServlet() {
        animalService = getAnimalService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var valueOpt = getLastPartOfUrl(urlToString);
        var value = valueOpt.orElseThrow();

        if (ANIMALS_ENDPOINT.equals(value)) {
            printAllAnimals(printWriter, resp);
        } else {
            printAnimalById(printWriter, value, resp);
        }
        printWriter.flush();
    }

    private void printAllAnimals(PrintWriter printWriter, HttpServletResponse resp) {
        var animals = animalService.findAll();
        var result = collectionToJson(animals);
        printWriter.print(result);
        resp.setStatus(OK);
    }

    private void printAnimalById(PrintWriter printWriter, String value, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(value);
            var dog = animalService.findById(id);
            var result = entityToJson(dog);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getLocalizedMessage());
        }
    }
}
