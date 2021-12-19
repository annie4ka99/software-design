package ru.akirakozov.sd.refactoring.response;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class GetProductsHandler extends QueryHandler {
    @Override
    void writeQueryResponse(PrintWriter responseWriter, ResultSet resultSet) {
        processSelectAllResult(responseWriter, resultSet);
    }

    @Override
    String getQuery() {
        return "SELECT * FROM PRODUCT";
    }
}
