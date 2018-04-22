package com.suman.spring.batch.dbconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Primary
    @Bean(name = "mysqlDB")
    @ConfigurationProperties(prefix = "spring.ds_mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDB") DataSource dsMysql) {
        return new JdbcTemplate(dsMysql);
    }

    @Bean(name = "martDB")
    @ConfigurationProperties(prefix = "spring.ds_mart")
    public DataSource martDB() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "martJdbcTemplate")
    public JdbcTemplate martJdbcTemplate(@Qualifier("martDB") DataSource dsMysql) {
        return new JdbcTemplate(dsMysql);
    }
}