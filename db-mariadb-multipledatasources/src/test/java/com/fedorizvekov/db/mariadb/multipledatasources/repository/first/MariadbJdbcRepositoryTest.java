package com.fedorizvekov.db.mariadb.multipledatasources.repository.first;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import com.fedorizvekov.db.mariadb.multipledatasources.config.MariadbConfig;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.impl.MariadbJdbcRepositoryImpl;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.impl.ResultSetMapperImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({MariadbJdbcRepositoryImpl.class, ResultSetMapperImpl.class})
@ContextConfiguration(classes = MariadbConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MariadbJdbcRepositoryTest {

    @Autowired
    private MariadbJdbcRepository repository;


    @Test
    public void shouldCount_rows() {
        long result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MARIADB first shard (Кириллица тест)");
        assertThat(result.getCharValue()).isEqualTo('A');
        assertThat(result.getLocalDateValue()).isEqualTo(LocalDate.parse("1990-01-31"));
        assertThat(result.getLocalTimeValue()).isEqualTo(LocalTime.parse("10:30:59"));
        assertThat(result.getLocalDateTimeValue()).isEqualTo(LocalDateTime.parse("1990-01-31T10:30:59"));
        assertThat(result.getByteValue()).isEqualTo((byte) 1);
        assertThat(result.getShortValue()).isEqualTo((short) 100);
        assertThat(result.getIntegerValue()).isEqualTo(1000);
        assertThat(result.getBigDecimalValue()).isEqualTo(BigDecimal.valueOf(1234.56));
        assertThat(result.getBooleanValue()).isEqualTo(true);
        assertThat(result.getUuidValue()).isEqualTo(UUID.fromString("1b6b2e07-78dc-43f5-9d94-bd77304a545c"));
    }


    @Test
    public void shouldFind_allRows() {
        List<TypeValue> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(1).getDatabaseName()).isEqualTo("MARIADB first shard (Кириллица тест)");
    }

}
