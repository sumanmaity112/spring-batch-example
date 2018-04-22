package com.suman.spring.batch.exports;

import com.suman.spring.batch.table.TableDataExtractor;
import com.suman.spring.batch.table.TableGeneratorStep;
import com.suman.spring.batch.table.domain.TableData;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class StepConfigurer {
    @Autowired
    private TableGeneratorStep tableGeneratorStep;

    @Qualifier("mysqlJdbcTemplate")
    @Autowired
    private JdbcTemplate mysqlJdbcTemplate;

    @Autowired
    private ExportStep exportStep;

    private static final String LIMIT = " LIMIT 1";
    private TableData tableData;


    public void createTables() {
        tableGeneratorStep.createTables(Collections.singletonList(tableData));
    }

    private void createTableData() {
        String sql = "select * from person";
        ResultSetExtractor<TableData> resultSetExtractor = new TableDataExtractor();
        tableData = mysqlJdbcTemplate.query(sql + LIMIT, resultSetExtractor);
        tableData.setName("person");
    }

    public Step getStep() {
        return exportStep.getStep();
    }

    @PostConstruct
    private void postConstruct(){
        createTableData();
        exportStep.setTableData(tableData);
    }
}
