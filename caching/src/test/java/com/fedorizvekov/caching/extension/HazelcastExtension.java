package com.fedorizvekov.caching.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class HazelcastExtension implements Extension {

    private static final int HAZELCAST_PORT = 5701;

    static {

        var HAZELCAST_CONTAINER = new GenericContainer<>(DockerImageName.parse("hazelcast/hazelcast"))
                .withExposedPorts(HAZELCAST_PORT);

        HAZELCAST_CONTAINER.start();

        var port = HAZELCAST_CONTAINER.getMappedPort(HAZELCAST_PORT);

        System.setProperty("spring.hazelcast.network", "localhost:" + port);
        System.setProperty("spring.hazelcast.cluster", "dev");
    }

}
