package com.suman.spring.batch.table;

import com.suman.spring.batch.table.domain.TableColumn;
import com.suman.spring.batch.table.domain.TableData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class TableDataExtractorTest {
    private TableDataExtractor tableDataExtractor;

    @Mock
    private ResultSet resultSet;

    @Mock
    private ResultSetMetaData resultSetMetaData;

    @Before
    public void setUp() {
        tableDataExtractor = new TableDataExtractor();
    }

    @Test
    public void shouldExtractTableDataFromResultSet() throws SQLException {
        when(resultSetMetaData.getColumnCount()).thenReturn(3);
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSetMetaData.getColumnLabel(1)).thenReturn("column_name");
        when(resultSetMetaData.getColumnLabel(2)).thenReturn("varchar_column_name");
        when(resultSetMetaData.getColumnLabel(3)).thenReturn("char_column_name");

        when(resultSetMetaData.getColumnTypeName(1)).thenReturn("INT");
        when(resultSetMetaData.getColumnTypeName(2)).thenReturn("VARCHAR");
        when(resultSetMetaData.getColumnTypeName(3)).thenReturn("CHAR");

        when(resultSetMetaData.getColumnType(1)).thenReturn(Types.INTEGER);
        when(resultSetMetaData.getColumnType(2)).thenReturn(Types.VARCHAR);
        when(resultSetMetaData.getColumnType(3)).thenReturn(Types.CHAR);

        when(resultSetMetaData.getColumnDisplaySize(2)).thenReturn(30);
        when(resultSetMetaData.getColumnDisplaySize(3)).thenReturn(5);

        TableData actualTableData = tableDataExtractor.extractData(resultSet);

        assertNotNull(actualTableData);
        List<TableColumn> tableColumns = actualTableData.getColumns();

        assertEquals(3, tableColumns.size());
        assertEquals("column_name", tableColumns.get(0).getName());
        assertEquals("varchar_column_name", tableColumns.get(1).getName());
        assertEquals("char_column_name", tableColumns.get(2).getName());

        assertEquals("int", tableColumns.get(0).getType().toLowerCase());
        assertEquals("varchar(30)", tableColumns.get(1).getType().toLowerCase());
        assertEquals("char(5)", tableColumns.get(2).getType().toLowerCase());

        verify(resultSetMetaData, times(3)).getColumnLabel(anyInt());
        verify(resultSetMetaData, times(3)).getColumnTypeName(anyInt());
        verify(resultSetMetaData, times(3)).getColumnType(anyInt());
        verify(resultSetMetaData, times(1)).getColumnDisplaySize(2);
        verify(resultSetMetaData, times(1)).getColumnDisplaySize(3);
    }
}