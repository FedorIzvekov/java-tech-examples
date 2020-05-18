package com.fedorizvekov.query.data.rest.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlExtension implements Extension {

    static {
        var POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE)
                .withDatabaseName("query_data_rest");

        POSTGRESQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRESQL_CONTAINER.getJdbcUrl() + "&currentSchema=test_schema");
        System.setProperty("spring.datasource.username", POSTGRESQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRESQL_CONTAINER.getPassword());
    }

}
