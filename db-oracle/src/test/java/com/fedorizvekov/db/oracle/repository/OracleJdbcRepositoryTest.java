package com.fedorizvekov.db.oracle.repository;

import static com.fedorizvekov.db.oracle.repository.OracleJpaRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import com.fedorizvekov.db.oracle.model.entity.TypeValue;
import com.fedorizvekov.db.oracle.repository.impl.OracleJdbcRepositoryImpl;
import com.fedorizvekov.db.oracle.repository.impl.RowMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({OracleJdbcRepositoryImpl.class, RowMapperImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OracleJdbcRepositoryTest {

    @Autowired
    private OracleJdbcRepository repository;


    @Test
    public void shouldCount_rows() {
        var result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        var result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("ORACLE (Кириллица тест)");
        assertThat(result.getCharValue()).isEqualTo(CHAR);
        assertThat(result.getLocalDateValue()).isEqualTo(LOCAL_DATE);
        assertThat(result.getLocalTimeValue().toLocalTime()).isEqualTo(LOCAL_TIME);
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
        var result = repository.findById(777L);

        assertThat(result).isInstanceOfAny(Optional.class);
        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void shouldFind_allRows() {
        var result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(1).getDatabaseName()).isEqualTo("ORACLE (Кириллица тест)");
    }

}
