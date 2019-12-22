package com.leverx.servletapp.animal.servlet;

import com.leverx.servletapp.animal.service.AnimalService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.constant.HttpResponseStatus.OK;
import static com.leverx.servletapp.factory.BeanFactory.getAnimalService;
import static com.leverx.servletapp.converter.EntityConverter.collectionToJson;
import static com.leverx.servletapp.util.ServletUtils.getLastPartOfUrl;
import static com.leverx.servletapp.util.constant.UrlComponent.ANIMALS_ENDPOINT;

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
        }
        printWriter.flush();
    }

    private void printAllAnimals(PrintWriter printWriter, HttpServletResponse resp) {
        var animals = animalService.findAll();
        var result = collectionToJson(animals);
        printWriter.print(result);
        resp.setStatus(OK);
    }
}
