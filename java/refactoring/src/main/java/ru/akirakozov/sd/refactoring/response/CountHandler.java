package ru.akirakozov.sd.refactoring.response;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class CountHandler extends QueryHandler {
    @Override
    void writeQueryResponse(PrintWriter responseWriter, ResultSet resultSet) {
        responseWriter.println("Number of products: ");
        processAggregatedResult(responseWriter, resultSet);
    }

    @Override
    String getQuery() {
        return "SELECT COUNT(price) FROM PRODUCT";
    }
}
