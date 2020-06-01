package com.fedorizvekov.caching.config;

import java.time.Duration;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
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


    @Bean
    public CaffeineCacheManager caffeineCacheManager() {

        var caffeine = Caffeine.newBuilder()
                .maximumSize(1000)                           // number of keys, that cache can have
                .expireAfterWrite(Duration.ofHours(1));

        var cacheManager = new CaffeineCacheManager("caffeineCache");
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}
