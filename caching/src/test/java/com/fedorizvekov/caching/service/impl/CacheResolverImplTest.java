package com.fedorizvekov.caching.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

@ExtendWith(MockitoExtension.class)
class CacheResolverImplTest {

    private CacheResolverImpl cacheResolver;

    @Mock
    private CacheOperationInvocationContext<?> context;
    @Mock
    private ConcurrentMapCacheManager simpleCacheManager;
    @Mock
    private CaffeineCacheManager caffeineCacheManager;
    @Mock
    private CouchbaseCacheManager couchbaseCacheManager;
    @Mock
    private HazelcastCacheManager hazelcastCacheManager;
    @Mock
    private RedisCacheManager redisCacheManager;
    @Mock
    private Cache cache;


    @BeforeEach
    void setUp() {
        cacheResolver = new CacheResolverImpl(
                simpleCacheManager,
                caffeineCacheManager,
                couchbaseCacheManager,
                hazelcastCacheManager,
                redisCacheManager
        );
    }


    @DisplayName("Should return CacheManager")
    @CsvSource({
            "'CAFFEINE', 'caffeineCache'",
            "'COUCHBASE', 'couchbaseCache'",
            "'HAZELCAST', 'hazelcastCache'",
            "'REDIS', 'redisCache'",
            "'SIMPLE', 'simpleCache'"
    })
    @ParameterizedTest
    void shouldReturnCacheManager(CacheType cacheType, String cacheName) {

        when(context.getArgs()).thenReturn(new Object[]{cacheType});

        switch (cacheType) {
            case CAFFEINE:
                when(caffeineCacheManager.getCache(anyString())).thenReturn(cache);
                break;
            case COUCHBASE:
                when(couchbaseCacheManager.getCache(anyString())).thenReturn(cache);
                break;
            case HAZELCAST:
                when(hazelcastCacheManager.getCache(anyString())).thenReturn(cache);
                break;
            case REDIS:
                when(redisCacheManager.getCache(anyString())).thenReturn(cache);
                break;
            case SIMPLE:
                when(simpleCacheManager.getCache(anyString())).thenReturn(cache);
                break;
        }

        cacheResolver.resolveCaches(context);

        switch (cacheType) {
            case CAFFEINE:
                verify(caffeineCacheManager).getCache(eq(cacheName));
                break;
            case COUCHBASE:
                verify(couchbaseCacheManager).getCache(eq(cacheName));
                break;
            case HAZELCAST:
                verify(hazelcastCacheManager).getCache(eq(cacheName));
                break;
            case REDIS:
                verify(redisCacheManager).getCache(eq(cacheName));
                break;
            case SIMPLE:
                verify(simpleCacheManager).getCache(eq(cacheName));
                break;
        }
    }

}
