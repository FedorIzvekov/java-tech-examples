package com.fedorizvekov.db.mariadb.multipledatasources.repository.first;

import static com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepositoryTest.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import com.fedorizvekov.db.mariadb.multipledatasources.config.MariadbConfig;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = MariadbConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MariadbJpaRepositoryTest {

    @Autowired
    private MariadbJpaRepository repository;


    @Test
    public void shouldCount_rows() {
        var result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        var result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MARIADB first shard (Кириллица тест)");
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
        assertThat(result.get(0)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(0).getDatabaseName()).isEqualTo("MARIADB first shard (Кириллица тест)");
    }

}
