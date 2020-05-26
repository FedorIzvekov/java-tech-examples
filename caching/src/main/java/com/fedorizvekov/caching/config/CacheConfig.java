package com.fedorizvekov.caching.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig {

    @Primary
    @Bean
    public ConcurrentMapCacheManager simpleCacheManager() {
        var cacheManager = new ConcurrentMapCacheManager("simpleCache");
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

}
