package com.fedorizvekov.caching.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fedorizvekov.caching.extension.PostgresqlExtension;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.impl.JdbcRepositoryImpl;
import com.fedorizvekov.caching.model.converter.CachedDataRowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(PostgresqlExtension.class)
@Import({JdbcRepositoryImpl.class, CachedDataRowMapper.class})
@JdbcTest
class JdbcRepositoryTest {

    @Autowired
    private JdbcRepository repository;


    @DisplayName("Should find all rows")
    @Test
    void shouldFind_allRows() {
        var result = repository.findAll();

        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0)).isInstanceOfAny(CachedData.class),
                () -> assertThat(result.get(0).getStringValue()).isEqualTo("First Raw"),
                () -> assertThat(result.get(1).getStringValue()).isEqualTo("Second Raw")
        );
    }

}
