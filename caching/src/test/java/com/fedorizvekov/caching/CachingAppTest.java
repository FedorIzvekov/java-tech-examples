package com.fedorizvekov.caching;

import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CachingAppTest {

    @MockBean
    private HikariDataSource hikariDataSource;
    @MockBean
    private JpaRepository jpaRepository;
    @MockBean
    private JdbcRepository jdbcRepository;
    @MockBean
    private LiquibaseAutoConfiguration.LiquibaseConfiguration liquibaseConfiguration;


    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
    }

}
