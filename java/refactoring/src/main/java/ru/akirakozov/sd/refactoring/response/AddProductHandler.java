package ru.akirakozov.sd.refactoring.response;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class AddProductHandler extends UpdateHandler {

    @Override
    String getQuery(HttpServletRequest request) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        return "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
    }

    @Override
    void writeResponse(PrintWriter responseWriter) {
        responseWriter.println("OK");
    }
}
