package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.response.AddProductHandler;
import ru.akirakozov.sd.refactoring.response.UpdateHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        UpdateHandler updateHandler = new AddProductHandler();
        updateHandler.processQuery(request, response);
    }
}
