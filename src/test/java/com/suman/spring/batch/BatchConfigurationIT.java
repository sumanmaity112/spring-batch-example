package com.suman.spring.batch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(profiles = "test")
public class BatchConfigurationIT {
    @Autowired
    private BatchConfiguration batchConfiguration;

    @Qualifier("martJdbcTemplate")
    @Autowired
    private JdbcTemplate martJdbcTemplate;


    @Before
    public void setUp() throws Exception {
        martJdbcTemplate.execute("DROP SCHEMA PUBLIC CASCADE ");
    }

    @Test
    public void shouldCopyPersonDataFromSourceDBToDestinationDB() throws Exception {
        batchConfiguration.run();
        List<Map<String, Object>> rows = martJdbcTemplate.queryForList("SELECT * FROM \"person\"");
        assertEquals(2, rows.size());

        HashMap<String, Object> row1 = new HashMap<String, Object>() {
            {
                put("person_id", 1);
                put("gender", "M");
                put("birthdate", null);
                put("birthdate_estimated", 0);
                put("dead", 0);
                put("death_date", null);
                put("cause_of_death", null);
                put("creator", null);
                put("changed_by", null);
                put("date_changed", null);
                put("voided", 0);
                put("voided_by", null);
                put("date_voided", null);
                put("void_reason", null);
                put("birthtime", null);
                put("deathdate_estimated", 0);
            }
        };

        HashMap<String, Object> row2 = new HashMap<String, Object>() {
            {
                put("person_id", 2);
                put("gender", "F");
                put("birthdate", null);
                put("birthdate_estimated", 0);
                put("dead", 0);
                put("death_date", null);
                put("cause_of_death", null);
                put("creator", 1);
                put("changed_by", null);
                put("date_changed", null);
                put("voided", 0);
                put("voided_by", null);
                put("date_voided", null);
                put("void_reason", null);
                put("birthtime", null);
                put("deathdate_estimated", 0);
            }
        };

        HashMap<Integer, HashMap<String, Object>> expectedRows = new HashMap<Integer, HashMap<String, Object>>() {
            {
                put(1, row1);
                put(2, row2);
            }
        };

        for (Map<String, Object> row : rows) {
            HashMap<String, Object> expectedRow = expectedRows.get(row.get("person_id"));

            for (String key : expectedRow.keySet()) {
                assertEquals(expectedRow.get(key), row.get(key));
            }
        }
    }
}
