package com.fedorizvekov.caching.service.impl;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.TestPropertySource;

@EnableCaching
@SpringBootTest
@TestPropertySource(properties = "spring.liquibase.enabled=false")
class CacheServiceImplTest {

    private final long ID = 1L;

    @Autowired
    private CacheServiceImpl cacheService;

    @MockBean
    private JpaRepository jpaRepository;
    @MockBean
    private JdbcRepository jdbcRepository;


    @DisplayName("Should invoke jpa find by id only once")
    @Test
    void shouldInvoke_jpaFindById_onlyOnce() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(CachedData.builder().id(ID).build()));

        cacheService.simpleFindById(ID);
        cacheService.simpleFindById(ID);

        verify(jpaRepository).findById(ID);
    }


    @DisplayName("Should invoke jdbc find all only once")
    @Test
    void shouldInvoke_jdbcFindAll_onlyOnce() {
        when(jpaRepository.findAll()).thenReturn(singletonList(CachedData.builder().id(ID).build()));

        cacheService.simpleFindAll();
        cacheService.simpleFindAll();

        verify(jdbcRepository).findAll();
    }

}
