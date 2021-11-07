package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class QueryServletTest extends ServletTest {
    private QueryServlet queryServlet = new QueryServlet();

    private String commandParameter = "command";

    @Override
    @Before
    public void setUp() throws Exception{
        super.setUp();

        String sqlInsert = "INSERT INTO " + tableName + " (" + nameColumn + ", " + priceColumn + ") VALUES"
                + "(\"" + name1 + "\", " + price1 + "), "
                + "(\"" + name2 + "\", " + price2 + ");";
        executeUpdate(sqlInsert);
    }

    @Test
    public void queryMax() throws Exception {
        String commandMax = "max";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandMax);

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter, times(4)).println(stringArgumentCaptor.capture());
        List<String> arguments =  stringArgumentCaptor.getAllValues();

        Assert.assertEquals(name2 + "\t" + price2 + "</br>", arguments.get(2));
    }

    @Test
    public void queryMin() throws Exception {
        String commandMin = "min";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandMin);

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter, times(4)).println(stringArgumentCaptor.capture());
        List<String> arguments =  stringArgumentCaptor.getAllValues();

        Assert.assertEquals(name1 + "\t" + price1 + "</br>", arguments.get(2));
    }

    @Test
    public void querySum() throws Exception {
        String commandSum = "sum";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandSum);

        doNothing().when(printWriter).println(anyInt());

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter, times(1)).println(anyInt());
        verify(printWriter, times(3)).println(stringArgumentCaptor.capture());
        verify(printWriter).println(price1 + price2);
    }

    @Test
    public void queryCount() throws Exception {
        String commandCount = "count";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandCount);

        doNothing().when(printWriter).println(anyInt());

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter, times(1)).println(anyInt());
        verify(printWriter, times(3)).println(anyString());
        verify(printWriter).println(2);
    }

    @Test
    public void queryUnknown() throws Exception {
        String commandUnknown = "unknown";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandUnknown);

        doNothing().when(printWriter).println(anyInt());

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter).println("Unknown command: " + commandUnknown);
    }


}
