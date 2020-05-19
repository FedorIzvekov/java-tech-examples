package com.fedorizvekov.db.clickhouse.extension;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class ClickhouseExtension implements Extension {

    private static final String CLICKHOUSE_USER = "test";
    private static final String CLICKHOUSE_PASSWORD = "test";
    private static final int CLICKHOUSE_PORT = 8123;


    static {

        var CLICKHOUSE_CONTAINER = new GenericContainer<>("clickhouse/clickhouse-server")
                .withExposedPorts(CLICKHOUSE_PORT)
                .withEnv("CLICKHOUSE_DB", "test_database")
                .withEnv("CLICKHOUSE_USER", CLICKHOUSE_USER)
                .withEnv("CLICKHOUSE_PASSWORD", CLICKHOUSE_PASSWORD)
                .waitingFor(Wait.forHttp("/ping").forStatusCode(200));

        CLICKHOUSE_CONTAINER.start();

        var port = CLICKHOUSE_CONTAINER.getMappedPort(CLICKHOUSE_PORT);
        var jdbcUrl = "jdbc:clickhouse://" + CLICKHOUSE_CONTAINER.getHost() + ":" + port + "/";

        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", CLICKHOUSE_USER);
        System.setProperty("spring.datasource.password", CLICKHOUSE_PASSWORD);

        initializeDatabase(jdbcUrl);

    }


    private static void initializeDatabase(String jdbcUrl) {

        try (var connection = DriverManager.getConnection(jdbcUrl, CLICKHOUSE_USER, CLICKHOUSE_PASSWORD)) {

            var createTable = new File("sql/clickhouse/001_create_table_type_value.sql");
            var insert = new File("sql/clickhouse/002_insert_rows.sql");

            ScriptUtils.executeSqlScript(connection, new FileSystemResource(createTable));
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(insert));

        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }

    }

}
