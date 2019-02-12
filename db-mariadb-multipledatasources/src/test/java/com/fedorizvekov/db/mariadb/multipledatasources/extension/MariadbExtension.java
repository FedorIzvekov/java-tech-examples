package com.fedorizvekov.db.mariadb.multipledatasources.extension;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.containers.MariaDBContainer;

public class MariadbExtension implements Extension {

    static {

        MariaDBContainer<?> FIRST_MARIADB_CONTAINER = new MariaDBContainer<>(MariaDBContainer.NAME)
                .withDatabaseName("multiple_data_sources");

        FIRST_MARIADB_CONTAINER.start();
        System.setProperty("datasource.first.url", FIRST_MARIADB_CONTAINER.getJdbcUrl());
        System.setProperty("datasource.first.username", FIRST_MARIADB_CONTAINER.getUsername());
        System.setProperty("datasource.first.password", FIRST_MARIADB_CONTAINER.getPassword());

        MariaDBContainer<?> SECOND_MARIADB_CONTAINER = new MariaDBContainer<>(MariaDBContainer.NAME)
                .withDatabaseName("multiple_data_sources");

        SECOND_MARIADB_CONTAINER.start();
        System.setProperty("datasource.second.url", SECOND_MARIADB_CONTAINER.getJdbcUrl());
        System.setProperty("datasource.second.username", SECOND_MARIADB_CONTAINER.getUsername());
        System.setProperty("datasource.second.password", SECOND_MARIADB_CONTAINER.getPassword());

        try (
                var firstConnection = DriverManager.getConnection(
                        FIRST_MARIADB_CONTAINER.getJdbcUrl(),
                        FIRST_MARIADB_CONTAINER.getUsername(),
                        FIRST_MARIADB_CONTAINER.getPassword()
                );
                var secondConnection = DriverManager.getConnection(
                        SECOND_MARIADB_CONTAINER.getJdbcUrl(),
                        SECOND_MARIADB_CONTAINER.getUsername(),
                        SECOND_MARIADB_CONTAINER.getPassword()
                )
        ) {

            var createTable = new File("sql/mariadb/001_create_table_type_value.sql");
            var datasourceFirstInsert = new File("sql/mariadb/002_datasource_first_insert.sql");
            var datasourceSecondInsert = new File("sql/mariadb/002_datasource_second_insert.sql");

            ScriptUtils.executeSqlScript(firstConnection, new FileSystemResource(createTable));
            ScriptUtils.executeSqlScript(firstConnection, new FileSystemResource(datasourceFirstInsert));

            ScriptUtils.executeSqlScript(secondConnection, new FileSystemResource(createTable));
            ScriptUtils.executeSqlScript(secondConnection, new FileSystemResource(datasourceSecondInsert));

        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
