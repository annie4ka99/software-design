package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.response.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        PrintWriter writer = response.getWriter();
        DBHandler dbHandler = getResponseHandler(command);
        if (dbHandler == null) {
            writer.println("Unknown command: " + command);
        } else {
            dbHandler.processQuery(request, response);
        }
    }

    private QueryHandler getResponseHandler(String command) {
        switch (command) {
            case "max":
                return new MaxHandler();
            case "min":
                return new MinHandler();
            case "sum":
                return new SumHandler();
            case "count":
                return new CountHandler();
            default: return null;
        }
    }

}
