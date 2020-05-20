package com.fedorizvekov.db.mssql.extension;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.containers.MSSQLServerContainer;

public class MssqlExtension implements Extension {

    static {

        var MSSQL_CONTAINER = new MSSQLServerContainer("mcr.microsoft.com/mssql/server")
                .acceptLicense();

        MSSQL_CONTAINER.withInitScript("init_mssql_database.sql");
        MSSQL_CONTAINER.withEnv("MSSQL_PID", "Developer");
        MSSQL_CONTAINER.start();

        var jdbcUrl = MSSQL_CONTAINER.getJdbcUrl() + ";databaseName=test_database";

        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "test_user");
        System.setProperty("spring.datasource.password", "TestUser#123");

        try (var connection = DriverManager.getConnection(
                jdbcUrl,
                MSSQL_CONTAINER.getUsername(),
                MSSQL_CONTAINER.getPassword())
        ) {
            var createSequence = new File("src/test/resources/004_create_sequence_seq_type_value.sql");
            var createTable = new File("src/test/resources/005_create_table_type_value.sql");
            var insertRows = new File("src/test/resources/006_insert_rows.sql");

            ScriptUtils.executeSqlScript(connection, new FileSystemResource(createSequence));
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(createTable));
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(insertRows));

        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
