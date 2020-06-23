package com.fedorizvekov.caching.service.impl;

import java.util.List;
import com.fedorizvekov.caching.exception.NotFoundException;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import com.fedorizvekov.caching.service.CacheService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final JpaRepository jpaRepository;
    private final JdbcRepository jdbcRepository;


    @Timed(value = "get.data.by.id.duration")
    @Cacheable(cacheResolver = "cacheResolverImpl", key = "#id")
    public CachedData findById(CacheType cacheType, long id) {

        return jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found CachedData with id '" + id + "'"));

    }


    @Timed(value = "get.all.data.duration")
    @Cacheable(cacheResolver = "cacheResolverImpl", key = "'all'")
    public List<CachedData> findAll(CacheType cacheType) {
        return jdbcRepository.findAll();
    }

}
