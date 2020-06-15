package com.fedorizvekov.caching.service.impl;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fedorizvekov.caching.extension.CouchbaseExtension;
import com.fedorizvekov.caching.extension.HazelcastExtension;
import com.fedorizvekov.caching.extension.RedisExtension;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith({CouchbaseExtension.class, HazelcastExtension.class, RedisExtension.class})
class CacheServiceImplTest {

    private final long ID = 1L;

    @Autowired
    private CacheServiceImpl cacheService;

    @MockBean
    private HikariDataSource hikariDataSource;
    @MockBean
    private JpaRepository jpaRepository;
    @MockBean
    private JdbcRepository jdbcRepository;
    @MockBean
    private LiquibaseAutoConfiguration.LiquibaseConfiguration liquibaseConfiguration;


    @DisplayName("Should invoke jpa find by id only once")
    @EnumSource(value = CacheType.class, names = {"CAFFEINE", "COUCHBASE", "HAZELCAST", "REDIS", "SIMPLE"})
    @ParameterizedTest
    void shouldInvoke_jpaFindById_onlyOnce(CacheType cacheType) {
        when(jpaRepository.findById(anyLong())).thenReturn(of(CachedData.builder().id(ID).build()));

        switch (cacheType) {
            case CAFFEINE:
                cacheService.caffeineFindById(ID);
                cacheService.caffeineFindById(ID);
                break;
            case COUCHBASE:
                cacheService.couchbaseFindById(ID);
                cacheService.couchbaseFindById(ID);
                break;
            case HAZELCAST:
                cacheService.hazelcastFindById(ID);
                cacheService.hazelcastFindById(ID);
                break;
            case REDIS:
                cacheService.redisFindById(ID);
                cacheService.redisFindById(ID);
                break;
            case SIMPLE:
                cacheService.simpleFindById(ID);
                cacheService.simpleFindById(ID);
                break;
        }

        verify(jpaRepository).findById(ID);
    }


    @DisplayName("Should invoke jdbc find all only once")
    @EnumSource(value = CacheType.class, names = {"CAFFEINE", "COUCHBASE", "HAZELCAST", "REDIS", "SIMPLE"})
    @ParameterizedTest
    void shouldInvoke_jdbcFindAll_onlyOnce(CacheType cacheType) {
        when(jpaRepository.findAll()).thenReturn(singletonList(CachedData.builder().id(ID).build()));

        switch (cacheType) {
            case CAFFEINE:
                cacheService.caffeineFindAll();
                cacheService.caffeineFindAll();
                break;
            case COUCHBASE:
                cacheService.couchbaseFindAll();
                cacheService.couchbaseFindAll();
                break;
            case HAZELCAST:
                cacheService.hazelcastFindAll();
                cacheService.hazelcastFindAll();
                break;
            case REDIS:
                cacheService.redisFindAll();
                cacheService.redisFindAll();
                break;
            case SIMPLE:
                cacheService.simpleFindAll();
                cacheService.simpleFindAll();
                break;
        }

        verify(jdbcRepository).findAll();
    }

}
