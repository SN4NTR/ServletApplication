package com.leverx.servletapp.cat.servlet;

import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.leverx.servletapp.cat.mapper.CatMapper.collectionToJson;
import static com.leverx.servletapp.cat.mapper.CatMapper.jsonToCatDto;
import static com.leverx.servletapp.cat.mapper.CatMapper.readJsonBody;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class CatServlet extends HttpServlet {

    private CatService catService = new CatServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var printWriter = resp.getWriter();
        var cats = catService.findAll();
        var result = collectionToJson(cats);
        printWriter.print(result);

        printWriter.flush();
        resp.setStatus(SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var reader = req.getReader();
        var jsonBody = readJsonBody(reader);
        var catDto = jsonToCatDto(jsonBody);

        catService.save(catDto);

        resp.setStatus(SC_CREATED);
    }
}
