package com.fedorizvekov.db.mariadb.multipledatasources.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.MariaDBContainer;

public class MariadbExtension implements Extension {

    static {
        MariaDBContainer<?> FIRST_MARIADB_CONTAINER = new MariaDBContainer<>(MariaDBContainer.NAME).withDatabaseName("multiple_data_sources");

        FIRST_MARIADB_CONTAINER.start();
        System.setProperty("datasource.first.url", FIRST_MARIADB_CONTAINER.getJdbcUrl());
        System.setProperty("datasource.first.username", FIRST_MARIADB_CONTAINER.getUsername());
        System.setProperty("datasource.first.password", FIRST_MARIADB_CONTAINER.getPassword());

        MariaDBContainer<?> SECOND_MARIADB_CONTAINER = new MariaDBContainer<>(MariaDBContainer.NAME).withDatabaseName("multiple_data_sources");

        SECOND_MARIADB_CONTAINER.start();

        System.setProperty("datasource.second.url", SECOND_MARIADB_CONTAINER.getJdbcUrl());
        System.setProperty("datasource.second.username", SECOND_MARIADB_CONTAINER.getUsername());
        System.setProperty("datasource.second.password", SECOND_MARIADB_CONTAINER.getPassword());
    }

}
