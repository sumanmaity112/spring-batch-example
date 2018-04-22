package com.suman.spring.batch.table;


import com.suman.spring.batch.table.domain.TableColumn;
import com.suman.spring.batch.table.domain.TableData;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class TableDataExtractor implements ResultSetExtractor<TableData> {

    @Override
    public TableData extractData(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        TableData currentTable = new TableData();

        for (int index = 1; index <= resultSetMetaData.getColumnCount(); index++) {
            currentTable.addColumn(new TableColumn(resultSetMetaData.getColumnLabel(index), getType(resultSetMetaData, index), false, null));
        }
        return currentTable;
    }

    private String getType(ResultSetMetaData resultSetMetaData, int index) throws SQLException {
        String columnTypeName = resultSetMetaData.getColumnTypeName(index);
        int columnType = resultSetMetaData.getColumnType(index);
        if (columnType == Types.VARCHAR || columnType==Types.CHAR) {
            return String.format("%s(%d)", columnTypeName, resultSetMetaData.getColumnDisplaySize(index));
        }
        return columnTypeName;
    }
}
