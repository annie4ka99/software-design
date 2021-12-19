package ru.akirakozov.sd.refactoring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public abstract class DBHandler {
    protected String dbConnectionUrl = "jdbc:sqlite:test.db";

    protected interface ThrowingConsumer<T, E extends Throwable> {
        void accept(T t) throws E;
    }

    protected void withDbConnection(ThrowingConsumer<Statement, Exception> callback){
        try (Connection c = DriverManager.getConnection(dbConnectionUrl)) {
            Statement stmt = c.createStatement();
            callback.accept(stmt);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processQuery(HttpServletRequest request, HttpServletResponse response) {
        withDbConnection(statement -> {
            process(statement, request, response);
        });
        setHeaders(response);
    }

    protected void setHeaders(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    abstract void process(Statement statement, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException;
}
