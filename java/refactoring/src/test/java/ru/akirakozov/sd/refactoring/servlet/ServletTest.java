package ru.akirakozov.sd.refactoring.servlet;

import org.junit.*;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public abstract class ServletTest {
    @Mock
    protected HttpServletRequest httpServletRequest;

    @Mock
    protected HttpServletResponse httpServletResponse;

    @Mock
    protected PrintWriter printWriter;

    @Captor
    protected ArgumentCaptor<String> stringArgumentCaptor;

    protected final String tableName = "PRODUCT";

    protected final String nameColumn = "NAME";
    protected final String priceColumn = "PRICE";

    protected final String name1 = "product1";
    protected final String name2 = "product2";
    protected final int price2 = 2;
    protected final int price1 = 1;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        cleanDatabase();

        doNothing().when(printWriter).println(anyString());

        when(httpServletResponse.getWriter()).thenReturn(printWriter);
        doNothing().when(httpServletResponse).setContentType("text/html");
        doNothing().when(httpServletResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @After
    public void truncate() throws SQLException {
        cleanDatabase();
        executeUpdate("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='"+ tableName + "';");
    }

    protected void cleanDatabase() throws SQLException {
        executeUpdate("delete from " + tableName);
    }

    protected void executeUpdate(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    protected List<HashMap<String,Object>> executeQuery(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            List<HashMap<String,Object>> res = convertResultSetToList(resultSet);
            stmt.close();
            return res;
        }
    }

    protected List<HashMap<String,Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String,Object>> res = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++)
                row.put(md.getColumnName(i), rs.getObject(i));
            res.add(row);
        }
        return res;
    }

}

