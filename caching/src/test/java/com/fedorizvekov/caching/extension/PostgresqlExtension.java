package com.fedorizvekov.caching.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlExtension implements Extension {

    static {
        PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE)
                .withDatabaseName("test_database")
                .withInitScript("init_postgresql_database.sql")
                .withCommand("postgres", "-c", "shared_preload_libraries=pg_stat_statements");

        POSTGRESQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRESQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRESQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRESQL_CONTAINER.getPassword());
    }

}
