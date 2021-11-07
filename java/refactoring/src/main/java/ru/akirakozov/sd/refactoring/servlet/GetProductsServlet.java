package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseProductServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        withDbConnection(statement -> {
            ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT");
            response.getWriter().println("<html><body>");

            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");

            rs.close();
        });

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
