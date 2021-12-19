package ru.akirakozov.sd.refactoring.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class QueryHandler extends DBHandler {
    @Override
    public void processQuery(HttpServletRequest request, HttpServletResponse response) {
        withDbConnection(statement -> {
            ResultSet rs = statement.executeQuery(getQuery());
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writeQueryResponse(response.getWriter(), rs);
            writer.println("</body></html>");
            rs.close();
        });
        setHeaders(response);
    }

    abstract void writeQueryResponse(PrintWriter responseWriter, ResultSet resultSet);

    protected void processSelectAllResult(PrintWriter writer, ResultSet rs) {
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                writer.println(name + "\t" + price + "</br>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processAggregatedResult(PrintWriter writer, ResultSet rs) {
        try {
            if (rs.next()) {
                writer.println(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    abstract String getQuery();
}
