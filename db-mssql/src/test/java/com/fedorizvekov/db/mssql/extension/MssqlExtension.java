package com.fedorizvekov.db.mssql.extension;

import org.junit.jupiter.api.extension.Extension;
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
    }

}
