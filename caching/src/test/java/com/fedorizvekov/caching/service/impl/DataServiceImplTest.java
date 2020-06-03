package com.fedorizvekov.caching.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.fedorizvekov.caching.exception.NotFoundException;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.service.CacheService;
import org.junit.jupiter.api.DisplayName;
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
    private final Optional<CachedData> cachedData = Optional.of(CachedData.builder().id(ID).build());

    @InjectMocks
    private DataServiceImpl dataService;

    @Mock
    private CacheService cacheService;


    @DisplayName("Should invoke correct findAll")
    @EnumSource(value = CacheType.class, names = {"CAFFEINE", "REDIS", "SIMPLE"})
    @ParameterizedTest
    void shouldInvokeCorrect_findAll(CacheType cacheType) {

        dataService.findAll(cacheType);

        switch (cacheType) {
            case CAFFEINE:
                verify(cacheService).caffeineFindAll();
                break;
            case REDIS:
                verify(cacheService).redisFindAll();
                break;
            case SIMPLE:
                verify(cacheService).simpleFindAll();
                break;
        }
    }


    @DisplayName("Should invoke correct findById")
    @EnumSource(value = CacheType.class, names = {"CAFFEINE", "REDIS", "SIMPLE"})
    @ParameterizedTest
    void shouldInvokeCorrect_findById(CacheType cacheType) {

        switch (cacheType) {
            case CAFFEINE:
                when(cacheService.caffeineFindById(anyLong())).thenReturn(cachedData);
                break;
            case REDIS:
                when(cacheService.redisFindById(anyLong())).thenReturn(cachedData);
                break;
            case SIMPLE:
                when(cacheService.simpleFindById(anyLong())).thenReturn(cachedData);
                break;
        }

        dataService.findById(cacheType, ID);

        switch (cacheType) {
            case CAFFEINE:
                verify(cacheService).caffeineFindById(ID);
                break;
            case REDIS:
                verify(cacheService).redisFindById(ID);
                break;
            case SIMPLE:
                verify(cacheService).simpleFindById(ID);
                break;
        }
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
