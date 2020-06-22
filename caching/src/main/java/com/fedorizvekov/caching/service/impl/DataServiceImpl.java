package com.fedorizvekov.caching.service.impl;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.caching.exception.NotFoundException;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.service.CacheService;
import com.fedorizvekov.caching.service.DataService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final CacheService cacheService;


    @Timed(value = "get.data.by.id.duration")
    public CachedData findById(CacheType cacheType, long id) {

        Optional<CachedData> cachedData;

        switch (cacheType) {
            case CAFFEINE:
                cachedData = cacheService.caffeineFindById(id);
                break;
            case COUCHBASE:
                cachedData = cacheService.couchbaseFindById(id);
                break;
            case HAZELCAST:
                cachedData = cacheService.hazelcastFindById(id);
                break;
            case REDIS:
                cachedData = cacheService.redisFindById(id);
                break;
            case SIMPLE:
            default:
                cachedData = cacheService.simpleFindById(id);
        }

        return cachedData.orElseThrow(() -> new NotFoundException("Not found CachedData with id '" + id + "'"));

    }


    @Timed(value = "get.all.data.duration")
    public List<CachedData> findAll(CacheType cacheType) {

        List<CachedData> cachedData;

        switch (cacheType) {
            case CAFFEINE:
                cachedData = cacheService.caffeineFindAll();
                break;
            case COUCHBASE:
                cachedData = cacheService.couchbaseFindAll();
                break;
            case HAZELCAST:
                cachedData = cacheService.hazelcastFindAll();
                break;
            case REDIS:
                cachedData = cacheService.redisFindAll();
                break;
            case SIMPLE:
            default:
                cachedData = cacheService.simpleFindAll();
        }

        return cachedData;

    }

}
