package com.fedorizvekov.caching.service.impl;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.autoconfigure.cache.CacheType.SIMPLE;

import com.fedorizvekov.caching.exception.NotFoundException;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.service.CacheService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.cache.CacheType;

@ExtendWith(MockitoExtension.class)
class DataServiceImplTest {

    private final long ID = 1L;

    @InjectMocks
    private DataServiceImpl dataService;

    @Mock
    private CacheService cacheService;


    @DisplayName("Should invoke simpleFindById")
    @Test
    void shouldInvoke_simpleFindById() {
        when(cacheService.simpleFindById(anyLong())).thenReturn(of(CachedData.builder().id(ID).build()));

        dataService.findById(SIMPLE, ID);
        verify(cacheService).simpleFindById(ID);
    }


    @DisplayName("Should invoke simpleFindAll")
    @Test
    void shouldInvoke_simpleFindAll() {
        dataService.findAll(SIMPLE);
        verify(cacheService).simpleFindAll();
    }


    @DisplayName("Should throw NotFoundException")
    @EnumSource(CacheType.class)
    @ParameterizedTest
    void shouldThrow_NotFoundException(CacheType cacheType) {
        assertThatThrownBy(() -> dataService.findById(cacheType, ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found CachedData with id '1'");
    }

}
