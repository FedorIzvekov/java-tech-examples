package com.fedorizvekov.caching.extension;

import org.junit.jupiter.api.extension.Extension;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;

public class CouchbaseExtension implements Extension {

    static {

        var COUCHBASE_CONTAINER = new CouchbaseContainer("couchbase/server")
                .withBucket(new BucketDefinition("test_bucket"));

        COUCHBASE_CONTAINER.start();

        System.setProperty("spring.couchbase.connection-string", COUCHBASE_CONTAINER.getConnectionString());
        System.setProperty("spring.couchbase.username", COUCHBASE_CONTAINER.getUsername());
        System.setProperty("spring.couchbase.password", COUCHBASE_CONTAINER.getPassword());
    }

}
