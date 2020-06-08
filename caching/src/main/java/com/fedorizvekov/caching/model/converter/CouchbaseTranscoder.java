package com.fedorizvekov.caching.model.converter;

import java.io.IOException;
import com.couchbase.client.core.msg.kv.CodecFlags;
import com.couchbase.client.java.codec.Transcoder;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CouchbaseTranscoder implements Transcoder {

    private final ObjectMapper objectMapper;


    public CouchbaseTranscoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public EncodedValue encode(Object input) {
        try {

            byte[] bytes = objectMapper.writeValueAsBytes(input);
            return new EncodedValue(bytes, CodecFlags.JSON_COMPAT_FLAGS);

        } catch (IOException exception) {
            throw new RuntimeException("IOException serialization Couchbase JSON, because: " + exception.getMessage());
        }
    }


    @Override
    public <T> T decode(Class<T> target, byte[] bytes, int flags) {
        try {

            return objectMapper.readValue(bytes, target);

        } catch (IOException exception) {
            throw new RuntimeException("IOException deserialization Couchbase JSON, because: " + exception.getMessage());
        }
    }

}
