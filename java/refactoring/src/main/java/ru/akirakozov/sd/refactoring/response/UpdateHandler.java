package ru.akirakozov.sd.refactoring.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public abstract class UpdateHandler extends DBHandler {
    @Override
    public void processQuery(HttpServletRequest request, HttpServletResponse response) {
        withDbConnection(statement -> {
            statement.executeUpdate(getQuery(request));
            writeResponse(response.getWriter());
        });

        setHeaders(response);
    }

    abstract String getQuery(HttpServletRequest request);

    abstract void writeResponse(PrintWriter responseWriter);
}
