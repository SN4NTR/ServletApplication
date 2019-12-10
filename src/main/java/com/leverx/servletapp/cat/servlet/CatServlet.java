package com.leverx.servletapp.cat.servlet;

import com.leverx.servletapp.cat.dto.CatInputDto;
import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.ValidationException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.constant.HttpResponseStatus.CREATED;
import static com.leverx.servletapp.constant.HttpResponseStatus.OK;
import static com.leverx.servletapp.converter.EntityConverter.collectionToJson;
import static com.leverx.servletapp.converter.EntityConverter.entityToJson;
import static com.leverx.servletapp.converter.EntityConverter.jsonToEntity;
import static com.leverx.servletapp.util.ServletUtils.getLastPartOfUrl;
import static com.leverx.servletapp.util.constant.UrlComponent.CATS_ENDPOINT;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class CatServlet extends HttpServlet {

    private CatService catService = new CatServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();

        var url = req.getRequestURL();
        var urlToString = url.toString();
        var valueOpt = getLastPartOfUrl(urlToString);
        var value = valueOpt.orElseThrow();

        if (CATS_ENDPOINT.equals(value)) {
            printAllCats(printWriter, resp);
        } else if (isParsable(value)) {
            printCatById(printWriter, value, resp);
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var reader = req.getReader();
            var catDto = jsonToEntity(reader, CatInputDto.class);
            catService.save(catDto);
            resp.setStatus(CREATED);
        } catch (ValidationException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getMessage());
        }
    }

    private void printAllCats(PrintWriter printWriter, HttpServletResponse resp) {
        var cats = catService.findAll();
        var result = collectionToJson(cats);
        printWriter.print(result);
        resp.setStatus(OK);
    }

    private void printCatById(PrintWriter printWriter, String value, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(value);
            var cat = catService.findById(id);
            var result = entityToJson(cat);
            printWriter.print(result);
            resp.setStatus(OK);
        } catch (EntityNotFoundException ex) {
            var responseStatus = ex.getResponseStatus();
            resp.sendError(responseStatus, ex.getMessage());
        }
    }
}
