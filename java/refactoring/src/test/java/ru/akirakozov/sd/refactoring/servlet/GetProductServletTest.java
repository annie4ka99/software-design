package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class GetProductServletTest extends ServletTest{
    private GetProductsServlet getProductsServlet = new GetProductsServlet();

    @Test
    public void getProduct() throws Exception {
        String sqlInsert = "INSERT INTO " + tableName + " (" + nameColumn + ", " + priceColumn + ") VALUES"
                + "(\"" + name1 + "\", " + price1 + "), "
                + "(\"" + name2 + "\", " + price2 + ");";
        executeUpdate(sqlInsert);

        getProductsServlet.doGet(httpServletRequest, httpServletResponse);

        InOrder inOrder = inOrder(printWriter);
        inOrder.verify(printWriter).println(pageStartHtml);
        inOrder.verify(printWriter).println(name1 + "\t" + price1 + "</br>");
        inOrder.verify(printWriter).println(name2 + "\t" + price2 + "</br>");
        inOrder.verify(printWriter).println(pageEndHtml);
        verifyNoMoreInteractions(printWriter);
    }
}
