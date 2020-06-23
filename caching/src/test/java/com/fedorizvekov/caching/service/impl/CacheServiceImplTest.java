package com.fedorizvekov.caching.service.impl;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.fedorizvekov.caching.exception.NotFoundException;
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
    private final long INVALID_ID = 2L;

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


    @DisplayName("Should invoke jpa only once")
    @EnumSource(value = CacheType.class, names = {"CAFFEINE", "COUCHBASE", "HAZELCAST", "REDIS", "SIMPLE"})
    @ParameterizedTest
    void shouldInvokeJpa_onlyOnce(CacheType cacheType) {

        // findById OK
        when(jpaRepository.findById(eq(ID))).thenReturn(of(CachedData.builder().id(ID).build()));

        cacheService.findById(cacheType, ID);
        cacheService.findById(cacheType, ID);

        verify(jpaRepository).findById(ID);


        // findAll OK
        when(jpaRepository.findAll()).thenReturn(singletonList(CachedData.builder().id(ID).build()));

        cacheService.findAll(cacheType);
        cacheService.findAll(cacheType);

        verify(jdbcRepository).findAll();


        // findById NotFoundException

        when(jpaRepository.findById(eq(INVALID_ID))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cacheService.findById(cacheType, INVALID_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found CachedData with id '2'");

        verify(jpaRepository).findById(INVALID_ID);
    }

}
