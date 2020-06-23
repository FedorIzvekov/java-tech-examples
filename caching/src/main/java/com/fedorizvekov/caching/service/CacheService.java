package com.fedorizvekov.caching.service;

import java.util.List;
import com.fedorizvekov.caching.model.entity.CachedData;
import org.springframework.boot.autoconfigure.cache.CacheType;

public interface CacheService {

    CachedData findById(CacheType cacheType, long id);

    List<CachedData> findAll(CacheType cacheType);

}
