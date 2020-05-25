package com.fedorizvekov.caching.service.impl;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CacheServiceImplTest {

    private final long ID = 1L;

    @InjectMocks
    private CacheServiceImpl cacheService;

    @Mock
    private JpaRepository jpaRepository;
    @Mock
    private JdbcRepository jdbcRepository;


    @DisplayName("Should invoke jpa find by id")
    @Test
    void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(CachedData.builder().id(ID).build()));

        cacheService.simpleFindById(ID);

        verify(jpaRepository).findById(ID);
    }


    @DisplayName("Should invoke jdbc find all")
    @Test
    void shouldInvoke_jdbcFindAll() {
        cacheService.simpleFindAll();
        verify(jdbcRepository).findAll();
    }

}
