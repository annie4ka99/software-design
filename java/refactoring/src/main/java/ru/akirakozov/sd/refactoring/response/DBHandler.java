package ru.akirakozov.sd.refactoring.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

    protected void setHeaders(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    abstract void processQuery(HttpServletRequest request, HttpServletResponse response);
}
