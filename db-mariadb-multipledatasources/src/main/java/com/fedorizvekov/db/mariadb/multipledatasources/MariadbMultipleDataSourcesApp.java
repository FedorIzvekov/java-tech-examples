package com.fedorizvekov.db.mariadb.multipledatasources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class MariadbMultipleDataSourcesApp {

    public static void main(String[] args) {
        SpringApplication.run(MariadbMultipleDataSourcesApp.class, args);
    }

}
