package com.fedorizvekov.caching.service.impl;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import com.fedorizvekov.caching.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final JpaRepository jpaRepository;
    private final JdbcRepository jdbcRepository;


    @Cacheable(value = "caffeineCache", cacheManager = "caffeineCacheManager")
    public Optional<CachedData> caffeineFindById(long id) {
        return jpaRepository.findById(id);
    }


    @Cacheable(value = "caffeineCache", cacheManager = "caffeineCacheManager")
    public List<CachedData> caffeineFindAll() {
        return jdbcRepository.findAll();
    }


    @Cacheable(value = "redisCache", cacheManager = "redisCacheManager", key = "#id")
    public Optional<CachedData> redisFindById(long id) {
        return jpaRepository.findById(id);
    }


    @Cacheable(value = "redisCache", cacheManager = "redisCacheManager", key = "'all'")
    public List<CachedData> redisFindAll() {
        return jdbcRepository.findAll();
    }


    @Cacheable(cacheNames = "simpleCache", cacheManager = "simpleCacheManager")
    public Optional<CachedData> simpleFindById(long id) {
        return jpaRepository.findById(id);
    }


    @Cacheable(cacheNames = "simpleCache", cacheManager = "simpleCacheManager")
    public List<CachedData> simpleFindAll() {
        return jdbcRepository.findAll();
    }

}
