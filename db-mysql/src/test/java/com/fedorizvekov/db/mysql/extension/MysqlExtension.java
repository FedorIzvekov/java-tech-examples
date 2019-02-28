package com.fedorizvekov.db.mysql.extension;

import java.util.TimeZone;
import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MySQLContainer;

public class MysqlExtension implements Extension {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer(MySQLContainer.NAME)
                .withDatabaseName("test_database");

        MYSQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", MYSQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", MYSQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", MYSQL_CONTAINER.getPassword());
    }

}
