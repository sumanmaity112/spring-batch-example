package com.suman.spring.batch.table;

import com.suman.spring.batch.table.domain.TableColumn;
import com.suman.spring.batch.table.domain.TableData;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TableDataProcessorTest {

    private TableDataProcessor tableDataProcessor;
    private Map<String, Object> items;
    private TableData tableData;

    @Before
    public void setUp() throws Exception {
        tableDataProcessor = new TableDataProcessor();
        items = new HashMap<String, Object>() {
            {
                put("person_id", 123);
                put("person_name", "test person");
                put("birthdate", "12/12/2017");
            }
        };
        tableData = new TableData();
        TableColumn tableColumn1 = new TableColumn("person_id", "integer", true, null);
        TableColumn tableColumn2 = new TableColumn("person_name", "varchar(29)", false, null);
        TableColumn tableColumn3 = new TableColumn("birthdate", "date", false, null);
        tableData.addAllColumns(Arrays.asList(tableColumn1, tableColumn2, tableColumn3));
        tableDataProcessor.setTableData(tableData);
    }

    @Test
    public void shouldReturnProcessedMapGivenItemsMap() throws Exception {

        Map<String, Object> processedMap = tableDataProcessor.process(items);

        assertNotNull(processedMap);
        assertTrue(processedMap.keySet().contains("person_id"));
        assertTrue(processedMap.keySet().contains("person_name"));
        assertTrue(processedMap.keySet().contains("birthdate"));
        assertThat(processedMap.get("person_id"), is("123"));
        assertThat(processedMap.get("person_name"), is("\'test person\'"));
        assertThat(processedMap.get("birthdate"), is("\'12/12/2017\'"));
    }

    @Test
    public void shouldReturnEmptyStringGivenItemValueIsNull() throws Exception {
        items.put("gender", null);
        TableColumn tableColumn4 = new TableColumn("gender", "integer", false, null);
        tableData.addColumn(tableColumn4);

        Map<String, Object> processedMap = tableDataProcessor.process(items);

        assertNotNull(processedMap);
        assertTrue(processedMap.keySet().contains("gender"));
        assertThat(processedMap.get("gender"), is(""));
    }

    @Test
    public void shouldReturnEmptyStringGivenItemValueIsEmpty() throws Exception {
        items.put("gender", "");
        TableColumn tableColumn4 = new TableColumn("gender", "integer", false, null);
        tableData.addColumn(tableColumn4);

        Map<String, Object> processedMap = tableDataProcessor.process(items);

        assertNotNull(processedMap);
        assertTrue(processedMap.keySet().contains("gender"));
        assertThat(processedMap.get("gender"), is(""));
    }
}