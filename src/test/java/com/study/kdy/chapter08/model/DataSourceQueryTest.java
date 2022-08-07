package com.study.kdy.chapter08.model;

import com.study.kdy.chapter08.config.DbConfig;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class DataSourceQueryTest {

    private AnnotationConfigApplicationContext dbAppCtx;

    @BeforeAll
    public void setup() {
        dbAppCtx = new AnnotationConfigApplicationContext(DbConfig.class);
    }

    @AfterAll
    public void close() {
        dbAppCtx.close();
    }

    @Order(1)
    @Test
    public void countTest() {
        var dataSourceQuery = new DataSourceQuery(dbAppCtx.getBean(DataSource.class));
        System.out.printf("dataSourceQuery class: %s%n", dataSourceQuery.getClass().getName());

        System.out.printf("maxIdle: %d", dataSourceQuery.getMaxIdle());

        var result = dataSourceQuery.count();
        System.out.println(result);
    }

}
