package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class AddProductServletTest extends ServletTest {
    private AddProductServlet addProductServlet = new AddProductServlet();

    @Test
    public void addProduct() throws Exception {
        String nameParameter = "name";
        when(httpServletRequest.getParameter(nameParameter)).thenReturn(name1, name2);
        String priceParameter = "price";
        when(httpServletRequest.getParameter(priceParameter)).thenReturn(
                String.valueOf(price1), String.valueOf(price2));

        addProductServlet.doGet(httpServletRequest, httpServletResponse);
        addProductServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter, times(2)).println("OK");
        verifyNoMoreInteractions(printWriter);

        String sqlQuery = "SELECT " +nameColumn + ", " + priceColumn + " FROM " + tableName;
        List<HashMap<String,Object>> res = executeQuery(sqlQuery);

        Assert.assertEquals(res.size(), 2);
        Assert.assertEquals(name1, res.get(0).get(nameColumn));
        Assert.assertEquals(price1, res.get(0).get(priceColumn));
        Assert.assertEquals(name2, res.get(1).get(nameColumn));
        Assert.assertEquals(price2, res.get(1).get(priceColumn));
    }
}
