package ru.akirakozov.sd.refactoring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UpdateHandler extends DBHandler {
    @Override
    protected void process(Statement statement,
                        HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        statement.executeUpdate(getQuery(request));
        writeResponse(response.getWriter());
    }

    abstract String getQuery(HttpServletRequest request);

    abstract void writeResponse(PrintWriter responseWriter);
}
