package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;

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

    protected void setHeaders(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void processHtmlResponse(HttpServletResponse response,
                                       ResponseHtmlBodyWriter writeResponse) {
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writeResponse.writeHtmlBody();
            writer.println("</body></html>");

            setHeaders(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected interface ResponseHtmlBodyWriter {
        void writeHtmlBody() throws SQLException;
    }

}
