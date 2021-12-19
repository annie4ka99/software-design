package ru.akirakozov.sd.refactoring.handler;

import java.io.PrintWriter;
import java.sql.ResultSet;

public class SumHandler extends QueryHandler {

    @Override
    void writeQueryResponse(PrintWriter responseWriter, ResultSet resultSet) {
        responseWriter.println("Summary price: ");
        processAggregatedResult(responseWriter, resultSet);
    }

    @Override
    String getQuery() {
        return "SELECT SUM(price) FROM PRODUCT";
    }
}
