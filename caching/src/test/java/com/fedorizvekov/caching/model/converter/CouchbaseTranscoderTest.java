package com.fedorizvekov.caching.model.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorizvekov.caching.config.CacheConfig;
import com.fedorizvekov.caching.model.entity.CachedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

@SpringBootTest(classes = {CouchbaseTranscoder.class, CacheConfig.class})
class CouchbaseTranscoderTest {

    @Autowired
    private CouchbaseTranscoder transcoder;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CaffeineCacheManager caffeineCacheManager;
    @MockBean
    private CouchbaseCacheManager couchbaseCacheManager;
    @MockBean
    private RedisCacheManager redisCacheManager;

    private CachedData cachedData;
    private String cachedDataString;


    @BeforeEach
    void setUp() {

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


    @DisplayName("should encode object to json")
    @Test
    void shouldEncodeObject_toJson() {
        var result = transcoder.encode(cachedData);

        assertThat(new String(result.encoded())).isEqualTo(cachedDataString);
    }


    @DisplayName("should decode json to object")
    @Test
    void shouldDecodeJson_toObject() {
        var result = transcoder.decode(CachedData.class, cachedDataString.getBytes(), 0);

        assertThat(result).isEqualTo(cachedData);
    }


    @DisplayName("should throw RuntimeException on serialization error")
    @Test
    void shouldThrowRuntimeException_onSerializationError() {
        assertThatThrownBy(() -> transcoder.encode(new Object()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("IOException serialization Couchbase JSON");
    }


    @DisplayName("should throw RuntimeException on deserialization error")
    @Test
    void shouldThrowRuntimeException_onDeserializationError() {
        assertThatThrownBy(() -> transcoder.decode(CachedData.class, "invalid json".getBytes(), 0))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("IOException deserialization Couchbase JSON");
    }

}
