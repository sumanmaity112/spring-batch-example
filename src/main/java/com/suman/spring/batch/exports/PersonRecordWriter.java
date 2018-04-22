package com.suman.spring.batch.exports;

import com.suman.spring.batch.helper.FreeMarkerEvaluator;
import com.suman.spring.batch.table.TableRecordHolder;
import com.suman.spring.batch.table.domain.TableData;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = "prototype")
public class PersonRecordWriter implements ItemWriter<Map<String, Object>> {

    @Autowired
    @Qualifier("martJdbcTemplate")
    private JdbcTemplate martJdbcTemplate;

    @Autowired
    private FreeMarkerEvaluator<TableRecordHolder> tableRecordHolderFreeMarkerEvaluator;

    private TableData tableData;

    @Override
    public void write(List<? extends Map<String, Object>> items) {
        List<Map<String, Object>> recordList = new ArrayList<>(items);
        TableRecordHolder tableRecordHolder = new TableRecordHolder(recordList, tableData.getName());
        String sql = tableRecordHolderFreeMarkerEvaluator.evaluate("insertRecord.ftl", tableRecordHolder);
        martJdbcTemplate.execute(sql);
    }

    public void setTableData(TableData tableData) {
        this.tableData = tableData;
    }

}
