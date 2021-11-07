package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public abstract class BaseProductServlet extends HttpServlet {
    protected final String dbConnectionUrl = "jdbc:sqlite:test.db";

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
}
