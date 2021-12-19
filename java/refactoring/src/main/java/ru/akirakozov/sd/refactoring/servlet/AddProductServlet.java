package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.handler.AddProductHandler;
import ru.akirakozov.sd.refactoring.handler.DBHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        DBHandler dbHandler = new AddProductHandler();
        dbHandler.processQuery(request, response);
    }
}
