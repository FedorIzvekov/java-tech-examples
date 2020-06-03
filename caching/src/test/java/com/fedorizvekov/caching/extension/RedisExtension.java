package com.fedorizvekov.caching.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.GenericContainer;

public class RedisExtension implements Extension {

    private static final int REDIS_PORT = 6379;
    private static final String REDIS_PASSWORD = "test_user_123";

    static {

        var REDIS_CONTAINER = new GenericContainer<>("redis")
                .withExposedPorts(REDIS_PORT)
                .withCommand("redis-server", "--requirepass", REDIS_PASSWORD);

        REDIS_CONTAINER.start();

        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port", REDIS_CONTAINER.getMappedPort(REDIS_PORT).toString());
        System.setProperty("spring.redis.password", REDIS_PASSWORD);
    }

}
