package com.leverx.servletapp.cat.servlet;

import com.leverx.servletapp.cat.entity.dto.CatInputDto;
import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.exception.EntityNotFoundException;
import com.leverx.servletapp.exception.InputDataException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.leverx.servletapp.mapper.EntityMapper.collectionToJson;
import static com.leverx.servletapp.mapper.EntityMapper.entityToJson;
import static com.leverx.servletapp.mapper.EntityMapper.jsonToEntity;
import static com.leverx.servletapp.util.ServletUtils.getLastPartOfUrl;
import static java.lang.Integer.parseInt;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

// TODO get rid of duplication
public class CatServlet extends HttpServlet {

    private CatService catService = new CatServiceImpl();

    private static final String CATS_ENDPOINT = "cats";

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
        var reader = req.getReader();
        var catDto = jsonToEntity(reader, CatInputDto.class);
        try {
            catService.save(catDto);
            resp.setStatus(SC_CREATED);
        } catch (InputDataException ex) {
            resp.sendError(SC_BAD_REQUEST, ex.getMessage());
        }
    }

    private void printAllCats(PrintWriter printWriter, HttpServletResponse resp) throws IOException {
        try {
            var cats = catService.findAll();
            var result = collectionToJson(cats);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } catch (EntityNotFoundException ex) {
            resp.sendError(SC_NOT_FOUND, ex.getMessage());
        }
    }

    private void printCatById(PrintWriter printWriter, String value, HttpServletResponse resp) throws IOException {
        try {
            var id = parseInt(value);
            var catOpt = catService.findById(id);
            var cat = catOpt.orElseThrow();
            var result = entityToJson(cat);
            printWriter.print(result);
            resp.setStatus(SC_OK);
        } catch (EntityNotFoundException ex) {
            resp.sendError(SC_NOT_FOUND, ex.getMessage());
        }
    }
}
