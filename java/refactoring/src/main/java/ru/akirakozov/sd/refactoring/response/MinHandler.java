package ru.akirakozov.sd.refactoring.response;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class MinHandler extends QueryHandler {
    @Override
    void writeQueryResponse(PrintWriter responseWriter, ResultSet resultSet) {
        responseWriter.println("<h1>Product with min price: </h1>");
        processSelectAllResult(responseWriter, resultSet);
    }

    @Override
    String getQuery() {
        return "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    }


}
