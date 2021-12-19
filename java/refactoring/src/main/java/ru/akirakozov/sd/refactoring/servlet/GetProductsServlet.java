package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.response.GetProductsHandler;
import ru.akirakozov.sd.refactoring.response.QueryHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        QueryHandler responseHandler = new GetProductsHandler();
        responseHandler.processQuery(request, response);
    }
}
