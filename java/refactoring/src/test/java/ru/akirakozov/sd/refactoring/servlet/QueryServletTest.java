package ru.akirakozov.sd.refactoring.servlet;

import org.junit.*;
import org.mockito.InOrder;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

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

        InOrder inOrder = inOrder(printWriter);
        inOrder.verify(printWriter).println(pageStartHtml);
        inOrder.verify(printWriter).println("<h1>Product with max price: </h1>");
        inOrder.verify(printWriter).println(name2 + "\t" + price2 + "</br>");
        inOrder.verify(printWriter).println(pageEndHtml);
    }

    @Test
    public void queryMin() throws Exception {
        String commandMin = "min";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandMin);

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        InOrder inOrder = inOrder(printWriter);
        inOrder.verify(printWriter).println(pageStartHtml);
        inOrder.verify(printWriter).println("<h1>Product with min price: </h1>");
        inOrder.verify(printWriter).println(name1 + "\t" + price1 + "</br>");
        inOrder.verify(printWriter).println(pageEndHtml);
    }

    @Test
    public void querySum() throws Exception {
        String commandSum = "sum";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandSum);

        doNothing().when(printWriter).println(anyInt());

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        InOrder inOrder = inOrder(printWriter);
        inOrder.verify(printWriter).println(pageStartHtml);
        inOrder.verify(printWriter).println("Summary price: ");
        inOrder.verify(printWriter).println(price1 + price2);
        inOrder.verify(printWriter).println(pageEndHtml);
    }

    @Test
    public void queryCount() throws Exception {
        String commandCount = "count";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandCount);

        doNothing().when(printWriter).println(anyInt());

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        InOrder inOrder = inOrder(printWriter);
        inOrder.verify(printWriter).println(pageStartHtml);
        inOrder.verify(printWriter).println("Number of products: ");
        inOrder.verify(printWriter).println(2);
        inOrder.verify(printWriter).println(pageEndHtml);
    }

    @Test
    public void queryUnknown() throws Exception {
        String commandUnknown = "unknown";
        when(httpServletRequest.getParameter(commandParameter)).thenReturn(commandUnknown);

        doNothing().when(printWriter).println(anyInt());

        queryServlet.doGet(httpServletRequest, httpServletResponse);

        verify(printWriter).println("Unknown command: " + commandUnknown);
    }

    @Override
    @After
    public void truncate() throws SQLException {
        verifyNoMoreInteractions(printWriter);

        super.truncate();
    }
}
