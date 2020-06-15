package com.fedorizvekov.caching.model.converter;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

public class HazelcastSerializer<T> implements StreamSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public HazelcastSerializer(ObjectMapper objectMapper, Class<T> type) {
        this.objectMapper = objectMapper;
        this.type = type;
    }


    @Override
    public void write(ObjectDataOutput out, T object) {
        try {

            var bytes = objectMapper.writeValueAsBytes(object);
            out.writeInt(bytes.length);
            out.write(bytes);

        } catch (IOException exception) {
            throw new RuntimeException("IOException serialization object to Hazelcast bytes, because: " + exception.getMessage());
        }
    }


    @Override
    public T read(ObjectDataInput in) {
        try {

            var length = in.readInt();
            var bytes = new byte[length];
            in.readFully(bytes);
            return objectMapper.readValue(bytes, type);

        } catch (IOException exception) {
            throw new RuntimeException("IOException deserialization Hazelcast bytes to object, because: " + exception.getMessage());
        }
    }


    @Override
    public int getTypeId() {
        return Math.abs(type.getName().hashCode());
    }


    @Override
    public void destroy() {
    }

}
