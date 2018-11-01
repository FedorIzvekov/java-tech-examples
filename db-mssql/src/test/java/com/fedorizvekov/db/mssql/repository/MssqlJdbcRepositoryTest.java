package com.fedorizvekov.db.mssql.repository;

import static com.fedorizvekov.db.mssql.repository.MssqlJpaRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import com.fedorizvekov.db.mssql.model.entity.TypeValue;
import com.fedorizvekov.db.mssql.repository.impl.MssqlJdbcRepositoryImpl;
import com.fedorizvekov.db.mssql.repository.impl.RowMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({MssqlJdbcRepositoryImpl.class, RowMapperImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MssqlJdbcRepositoryTest {

    @Autowired
    private MssqlJdbcRepository repository;


    @Test
    public void shouldCount_rows() {
        var result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        var result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MSSQL (Кириллица тест)");
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
        var result = repository.findById(777L);

        assertThat(result).isInstanceOfAny(Optional.class);
        assertThat(result.isPresent()).isFalse();
    }


    @Test
    public void shouldFind_allRows() {
        var result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(1).getDatabaseName()).isEqualTo("MSSQL (Кириллица тест)");
    }

}
