package com.fedorizvekov.db.mysql.extension;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
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

        try (var connection = DriverManager.getConnection(
                MYSQL_CONTAINER.getJdbcUrl(),
                MYSQL_CONTAINER.getUsername(),
                MYSQL_CONTAINER.getPassword())
        ) {

            var createTable = new File("sql/mysql/001_create_table_type_value.sql");
            var insert = new File("sql/mysql/002_insert_rows.sql");

            ScriptUtils.executeSqlScript(connection, new FileSystemResource(createTable));
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(insert));

        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
