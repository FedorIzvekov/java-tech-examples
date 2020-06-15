package com.fedorizvekov.caching.model.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorizvekov.caching.config.CacheConfig;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

@SpringBootTest(classes = {CacheConfig.class})
class HazelcastSerializerTest {

    private HazelcastSerializer<CachedData> serializer;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CaffeineCacheManager caffeineCacheManager;
    @MockBean
    private CouchbaseCacheManager couchbaseCacheManager;
    @MockBean
    private RedisCacheManager redisCacheManager;
    @MockBean
    private HazelcastCacheManager hazelcastCacheManager;

    @Mock
    private ObjectDataOutput dataOutput;
    @Mock
    private ObjectDataInput dataInput;

    private CachedData cachedData;
    private String cachedDataString;


    @BeforeEach
    void setUp() {

        serializer = new HazelcastSerializer<>(objectMapper, CachedData.class);

        cachedData = CachedData.builder()
                .id(1L)
                .stringValue("test string")
                .localDateValue(LocalDate.parse("1990-01-31"))
                .localTimeValue(LocalTime.parse("10:30:59"))
                .localDateTimeValue(LocalDateTime.parse("1990-01-31T10:30:59"))
                .integerValue(111)
                .bigDecimalValue(new BigDecimal("99999999999999999.99"))
                .booleanValue(true)
                .build();

        cachedDataString = "[\"com.fedorizvekov.caching.model.entity.CachedData\","
                + "{\"id\":1,\"stringValue\":\"test string\","
                + "\"localDateValue\":[1990,1,31],"
                + "\"localTimeValue\":[10,30,59],"
                + "\"localDateTimeValue\":[1990,1,31,10,30,59],"
                + "\"integerValue\":111,"
                + "\"bigDecimalValue\":[\"java.math.BigDecimal\",99999999999999999.99],"
                + "\"booleanValue\":true}]";
    }


    @DisplayName("should serialize object to bytes")
    @Test
    void shouldSerializeObject_toBytes() throws Exception {
        var byteCaptor = ArgumentCaptor.forClass(byte[].class);

        serializer.write(dataOutput, cachedData);

        verify(dataOutput).write(byteCaptor.capture());

        assertThat(byteCaptor.getValue()).isEqualTo(cachedDataString.getBytes());
    }


    @DisplayName("should deserialize bytes to object")
    @Test
    void shouldDeserializeBytes_toObject() throws Exception {
        var bytes = cachedDataString.getBytes();

        when(dataInput.readInt()).thenReturn(bytes.length);
        doAnswer(invocation -> {
            System.arraycopy(bytes, 0, invocation.getArgument(0), 0, bytes.length);
            return invocation;
        }).when(dataInput).readFully(any(byte[].class));

        var result = serializer.read(dataInput);

        assertThat(result).isEqualTo(cachedData);
    }


    @DisplayName("should throw RuntimeException on serialization error")
    @Test
    void shouldThrowRuntimeException_onSerializationError() {
        assertThatThrownBy(() -> serializer.write(dataOutput, mock(CachedData.class)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("IOException serialization object to Hazelcast bytes");
    }


    @DisplayName("should throw RuntimeException on deserialization error")
    @Test
    void shouldThrowRuntimeException_onDeserializationError() {
        assertThatThrownBy(() -> serializer.read(dataInput))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("IOException deserialization Hazelcast bytes to object");
    }

}
