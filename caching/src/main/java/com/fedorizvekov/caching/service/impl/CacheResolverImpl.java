package com.fedorizvekov.caching.service.impl;

import static java.util.Collections.singletonList;
import static org.springframework.boot.autoconfigure.cache.CacheType.CAFFEINE;
import static org.springframework.boot.autoconfigure.cache.CacheType.COUCHBASE;
import static org.springframework.boot.autoconfigure.cache.CacheType.HAZELCAST;
import static org.springframework.boot.autoconfigure.cache.CacheType.REDIS;
import static org.springframework.boot.autoconfigure.cache.CacheType.SIMPLE;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheResolverImpl implements CacheResolver {

    private final Map<CacheType, CacheManager> cacheManagers = new HashMap<>();


    public CacheResolverImpl(
            ConcurrentMapCacheManager simpleCacheManager,
            CaffeineCacheManager caffeineCacheManager,
            CouchbaseCacheManager couchbaseCacheManager,
            HazelcastCacheManager hazelcastCacheManager,
            RedisCacheManager redisCacheManager
    ) {
        cacheManagers.put(SIMPLE, simpleCacheManager);
        cacheManagers.put(CAFFEINE, caffeineCacheManager);
        cacheManagers.put(COUCHBASE, couchbaseCacheManager);
        cacheManagers.put(HAZELCAST, hazelcastCacheManager);
        cacheManagers.put(REDIS, redisCacheManager);
    }


    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {

        var cacheType = (CacheType) context.getArgs()[0];

        var cacheManager = cacheManagers.getOrDefault(cacheType, cacheManagers.get(SIMPLE));

        return singletonList(cacheManager.getCache(cacheType.name().toLowerCase() + "Cache"));

    }

}
