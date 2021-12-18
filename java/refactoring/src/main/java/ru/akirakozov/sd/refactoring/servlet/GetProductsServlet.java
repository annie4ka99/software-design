package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseProductServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        withDbConnection(statement -> {
            ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT");
            PrintWriter writer = response.getWriter();
            processHtmlResponse(response, () -> {
                while (rs.next()) {
                    String  name = rs.getString("name");
                    int price  = rs.getInt("price");
                    writer.println(name + "\t" + price + "</br>");
                }
            });
            rs.close();
        });
    }
}
