package com.suman.spring.batch.exports;

import com.suman.spring.batch.table.TableDataProcessor;
import com.suman.spring.batch.table.domain.TableData;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class ExportStep {

    @Autowired
    private ObjectFactory<PersonRecordWriter> recordWriterObjectFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    private TableData tableData;

    public Step getStep() {
        return stepBuilderFactory.get("Data Export Step")
                .<Map<String, Object>, Map<String, Object>>chunk(100)
                .reader(getReader())
                .processor(getProcessor())
                .writer(getWriter())
                .build();
    }

    private PersonRecordWriter getWriter() {
        PersonRecordWriter writer = recordWriterObjectFactory.getObject();
        writer.setTableData(tableData);
        return writer;
    }

    private TableDataProcessor getProcessor() {
        TableDataProcessor tableDataProcessor = new TableDataProcessor();
        tableDataProcessor.setTableData(tableData);
        return tableDataProcessor;
    }

    private JdbcCursorItemReader<Map<String, Object>> getReader() {
        String readerSql = "select * from person";
        JdbcCursorItemReader<Map<String, Object>> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(readerSql);
        reader.setRowMapper(new ColumnMapRowMapper());
        return reader;
    }

    public void setTableData(TableData tableData) {
        this.tableData = tableData;
    }

}
