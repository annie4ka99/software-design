package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetProductServletTest extends ServletTest{
    private GetProductsServlet getProductsServlet = new GetProductsServlet();

    @Test
    public void getProduct() throws Exception {
        String sqlInsert = "INSERT INTO " + tableName + " (" + nameColumn + ", " + priceColumn + ") VALUES"
                + "(\"" + name1 + "\", " + price1 + "), "
                + "(\"" + name2 + "\", " + price2 + ");";
        executeUpdate(sqlInsert);

        getProductsServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter, times(4)).println(stringArgumentCaptor.capture());
        List<String> arguments =  stringArgumentCaptor.getAllValues();

        Assert.assertEquals(4, arguments.size());
        Assert.assertEquals(name1 + "\t" + price1 + "</br>", arguments.get(1));
        Assert.assertEquals(name2 + "\t" + price2 + "</br>", arguments.get(2));
    }
}
