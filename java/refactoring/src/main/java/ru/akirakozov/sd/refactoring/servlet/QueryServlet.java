package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class QueryServlet extends BaseProductServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        PrintWriter writer = response.getWriter();
        if ("max".equals(command)) {
            withDbConnection(statement -> {
                ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                processHtmlResponse(response, () -> {
                    writer.println("<h1>Product with max price: </h1>");

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        writer.println(name + "\t" + price + "</br>");
                    }
                });

                rs.close();
            });
        } else if ("min".equals(command)) {
            withDbConnection(statement -> {
                ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                processHtmlResponse(response, () -> {
                    writer.println("<h1>Product with min price: </h1>");

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        writer.println(name + "\t" + price + "</br>");
                    }
                });

                rs.close();
            });
        } else if ("sum".equals(command)) {
            withDbConnection(statement -> {
                ResultSet rs = statement.executeQuery("SELECT SUM(price) FROM PRODUCT");
                processHtmlResponse(response, () -> {
                    writer.println("Summary price: ");
                    if (rs.next()) {
                        writer.println(rs.getInt(1));
                    }
                });
                rs.close();
            });
        } else if ("count".equals(command)) {
            withDbConnection(statement -> {
                ResultSet rs = statement.executeQuery("SELECT COUNT(price) FROM PRODUCT");
                processHtmlResponse(response, () -> {
                    writer.println("Number of products: ");
                    if (rs.next()) {
                        writer.println(rs.getInt(1));
                    }
                });
                rs.close();
            });
        } else {
            writer.println("Unknown command: " + command);
        }
    }

}
