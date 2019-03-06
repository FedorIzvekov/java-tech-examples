package com.fedorizvekov.db.postgresql.extension;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresqlExtension implements Extension {

    static {
        PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE)
                .withDatabaseName("test_database");

        POSTGRESQL_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRESQL_CONTAINER.getJdbcUrl() + "&currentSchema=test_schema");
        System.setProperty("spring.datasource.username", POSTGRESQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRESQL_CONTAINER.getPassword());

        try (var connection = DriverManager.getConnection(
                POSTGRESQL_CONTAINER.getJdbcUrl(),
                POSTGRESQL_CONTAINER.getUsername(),
                POSTGRESQL_CONTAINER.getPassword())
        ) {

            var createSchema = new File("sql/postgresql/001_create_schema.sql");
            var createTable = new File("sql/postgresql/002_create_table_type_value.sql");
            var insertRows = new File("sql/postgresql/003_insert_rows.sql");

            ScriptUtils.executeSqlScript(connection, new FileSystemResource(createSchema));
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(createTable));
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(insertRows));

        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
