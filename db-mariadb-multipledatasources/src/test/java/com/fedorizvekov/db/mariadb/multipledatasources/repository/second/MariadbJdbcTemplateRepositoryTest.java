package com.fedorizvekov.db.mariadb.multipledatasources.repository.second;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import com.fedorizvekov.db.mariadb.multipledatasources.config.MariadbConfig;
import com.fedorizvekov.db.mariadb.multipledatasources.config.SecondMariadbConfig;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.impl.MariadbJdbcTemplateRepositoryImpl;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.impl.RowMapperImpl;
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
@Import({MariadbJdbcTemplateRepositoryImpl.class, RowMapperImpl.class})
@ContextConfiguration(classes = {MariadbConfig.class, SecondMariadbConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MariadbJdbcTemplateRepositoryTest {

    @Autowired
    private MariadbJdbcTemplateRepository repository;


    @Test
    public void shouldCount_rows() {
        long result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MARIADB second shard (Кириллица тест)");
        assertThat(result.getCharValue()).isEqualTo('B');
        assertThat(result.getLocalDateValue()).isEqualTo(LocalDate.parse("2000-12-01"));
        assertThat(result.getLocalTimeValue()).isEqualTo(LocalTime.parse("10:30:59"));
        assertThat(result.getLocalDateTimeValue()).isEqualTo(LocalDateTime.parse("2000-12-01T10:30:59"));
        assertThat(result.getByteValue()).isEqualTo((byte) 2);
        assertThat(result.getShortValue()).isEqualTo((short) 200);
        assertThat(result.getIntegerValue()).isEqualTo(2000);
        assertThat(result.getBigDecimalValue()).isEqualTo(BigDecimal.valueOf(5678.91));
        assertThat(result.getBooleanValue()).isEqualTo(false);
        assertThat(result.getUuidValue()).isEqualTo(UUID.fromString("afef2790-2dcb-4447-bae3-a34dbed182af"));
    }


    @Test
    public void shouldFind_allRows() {
        List<TypeValue> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(1).getDatabaseName()).isEqualTo("MARIADB second shard (Кириллица тест)");
    }

}
