package com.fedorizvekov.caching.config;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

import java.time.Duration;
import com.couchbase.client.java.Cluster;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fedorizvekov.caching.model.converter.CouchbaseTranscoder;
import com.fedorizvekov.caching.model.converter.HazelcastSerializer;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return objectMapper;
    }


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


    @Bean
    public CouchbaseCacheManager couchbaseCacheManager(
            Cluster cluster,
            @Value("${spring.couchbase.bucket}") String bucket,
            ObjectMapper objectMapper
    ) {

        var config = CouchbaseCacheConfiguration.defaultCacheConfig()
                .entryExpiry(Duration.ofHours(1))
                .valueTranscoder(new CouchbaseTranscoder(objectMapper))
                .disableCachingNullValues();

        return CouchbaseCacheManager
                .builder(new SimpleCouchbaseClientFactory(cluster, bucket, "_default"))
                .cacheDefaults(config)
                .withCacheConfiguration("couchbaseCache", config)
                .build();
    }


    @Bean
    public HazelcastCacheManager hazelcastCacheManager(
            @Value("${spring.hazelcast.network}") String network,
            @Value("${spring.hazelcast.cluster}") String cluster,
            ObjectMapper objectMapper
    ) {

        var clientConfig = new ClientConfig();
        clientConfig.setClusterName(cluster);
        clientConfig.getNetworkConfig().addAddress(network);

        clientConfig.getSerializationConfig().addSerializerConfig(
                new SerializerConfig()
                        .setTypeClass(CachedData.class)
                        .setImplementation(new HazelcastSerializer<>(objectMapper, CachedData.class))
        );

        return new HazelcastCacheManager(HazelcastClient.newHazelcastClient(clientConfig));
    }


    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {

        var config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                .disableCachingNullValues();

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .withCacheConfiguration("redisCache", config)
                .build();
    }

}
