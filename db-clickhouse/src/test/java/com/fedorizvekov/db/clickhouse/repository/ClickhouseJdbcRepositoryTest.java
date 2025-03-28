package com.fedorizvekov.db.clickhouse.repository;

import static com.fedorizvekov.db.clickhouse.repository.ClickhouseJpaRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import com.fedorizvekov.db.clickhouse.extension.ClickhouseExtension;
import com.fedorizvekov.db.clickhouse.model.entity.TypeValue;
import com.fedorizvekov.db.clickhouse.repository.impl.ClickhouseJdbcRepositoryImpl;
import com.fedorizvekov.db.clickhouse.repository.impl.RowMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(ClickhouseExtension.class)
@Import({ClickhouseJdbcRepositoryImpl.class, RowMapperImpl.class})
@JdbcTest
class ClickhouseJdbcRepositoryTest {

    @Autowired
    private ClickhouseJdbcRepository repository;


    @DisplayName("Should count rows")
    @Test
    void shouldCount_rows() {
        var result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @DisplayName("Should find row by id")
    @Test
    void shouldFind_rowById() {
        var result = repository.findById(1L).get();

        assertAll(
                () -> assertThat(result).isInstanceOfAny(TypeValue.class),
                () -> assertThat(result.getDatabaseName()).isEqualTo("CLICKHOUSE (Кириллица тест)"),
                () -> assertThat(result.getCharValue()).isEqualTo(CHAR),
                () -> assertThat(result.getLocalDateValue()).isEqualTo(LOCAL_DATE),
                () -> assertThat(result.getLocalTimeValue()).isEqualTo(LOCAL_TIME),
                () -> assertThat(result.getLocalDateTimeValue()).isEqualTo(LOCAL_DATE_TIME),
                () -> assertThat(result.getByteValue()).isEqualTo(BYTE),
                () -> assertThat(result.getShortValue()).isEqualTo(SHORT),
                () -> assertThat(result.getIntegerValue()).isEqualTo(INTEGER),
                () -> assertThat(result.getBigDecimalValue()).isEqualTo(BIG_DECIMAL),
                () -> assertThat(result.getBooleanValue()).isEqualTo(BOOLEAN),
                () -> assertThat(result.getUuidValue()).isEqualTo(UUID_TEST)
        );
    }


    @DisplayName("Should not found row by id")
    @Test
    void shouldNotFound_rowById() {
        var result = repository.findById(777L);

        assertAll(
                () -> assertThat(result).isInstanceOfAny(Optional.class),
                () -> assertThat(result.isPresent()).isFalse()
        );
    }


    @DisplayName("Should find all rows")
    @Test
    void shouldFind_allRows() {
        var result = repository.findAll();

        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(1)).isInstanceOfAny(TypeValue.class),
                () -> assertThat(result.get(1).getDatabaseName()).isEqualTo("CLICKHOUSE (Кириллица тест)")
        );
    }

}
