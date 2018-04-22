package com.suman.spring.batch.table.domain;

import java.util.ArrayList;
import java.util.List;

public class TableData {
    private String name;
    private List<TableColumn> columns;

    public TableData(String name) {
        this.name = name;
        this.columns = new ArrayList<TableColumn>();
    }

    public TableData() {
        this(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }

    public void addColumn(TableColumn column) {
        this.columns.add(column);
    }

    public void addAllColumns(List<TableColumn> columns) {
        this.columns.addAll(columns);
    }
}
