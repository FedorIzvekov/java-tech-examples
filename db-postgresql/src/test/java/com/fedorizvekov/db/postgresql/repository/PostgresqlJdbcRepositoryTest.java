package com.fedorizvekov.db.postgresql.repository;

import static com.fedorizvekov.db.postgresql.repository.PostgresqlJpaRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.postgresql.model.entity.TypeValue;
import com.fedorizvekov.db.postgresql.repository.impl.PostgresqlJdbcRepositoryImpl;
import com.fedorizvekov.db.postgresql.repository.impl.RowMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({PostgresqlJdbcRepositoryImpl.class, RowMapperImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostgresqlJdbcRepositoryTest {

    @Autowired
    private PostgresqlJdbcRepository repository;


    @Test
    public void shouldCount_rows() {
        long result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("POSTGRESQL (Кириллица тест)");
        assertThat(result.getCharValue()).isEqualTo(CHAR);
        assertThat(result.getLocalDateValue()).isEqualTo(LOCAL_DATE);
        assertThat(result.getLocalTimeValue()).isEqualTo(LOCAL_TIME);
        assertThat(result.getLocalDateTimeValue()).isEqualTo(LOCAL_DATE_TIME);
        assertThat(result.getByteValue()).isEqualTo(BYTE);
        assertThat(result.getShortValue()).isEqualTo(SHORT);
        assertThat(result.getIntegerValue()).isEqualTo(INTEGER);
        assertThat(result.getBigDecimalValue()).isEqualTo(BIG_DECIMAL);
        assertThat(result.getBooleanValue()).isEqualTo(BOOLEAN);
        assertThat(result.getUuidValue()).isEqualTo(UUID_TEST);
    }


    @Test
    public void shouldNotFound_rowById() {
        Optional<TypeValue> result = repository.findById(777L);

        assertThat(result).isInstanceOfAny(Optional.class);
        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void shouldFind_allRows() {
        List<TypeValue> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(1).getDatabaseName()).isEqualTo("POSTGRESQL (Кириллица тест)");
    }

}
