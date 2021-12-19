package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.handler.DBHandler;
import ru.akirakozov.sd.refactoring.handler.GetProductsHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DBHandler dbHandler = new GetProductsHandler();
        dbHandler.processQuery(request, response);
    }
}
