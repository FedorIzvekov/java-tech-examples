package com.fedorizvekov.caching;

import com.couchbase.client.java.Cluster;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;

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
    @MockBean
    private Cluster cluster;
    @MockBean
    private CouchbaseCacheManager couchbaseCacheManager;
    @MockBean
    private HazelcastCacheManager hazelcastCacheManager;


    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
    }

}
