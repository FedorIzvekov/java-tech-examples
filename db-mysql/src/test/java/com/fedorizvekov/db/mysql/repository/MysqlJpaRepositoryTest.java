package com.fedorizvekov.db.mysql.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MysqlJpaRepositoryTest {

    public static final char CHAR = 'A';
    public static final LocalDate LOCAL_DATE = LocalDate.parse("1990-01-31");
    public static final LocalTime LOCAL_TIME = LocalTime.parse("10:30:59");
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.parse("1990-01-31T10:30:59");
    public static final Byte BYTE = 127;
    public static final Short SHORT = 32767;
    public static final Integer INTEGER = 2147483647;
    public static final BigDecimal BIG_DECIMAL = new BigDecimal("99999999999999999.99");
    public static final Boolean BOOLEAN = true;
    public static final UUID UUID_TEST = UUID.fromString("1b6b2e07-78dc-43f5-9d94-bd77304a545c");

    @Autowired
    private MysqlJpaRepository repository;


    @Test
    public void shouldCount_rows() {
        long result = repository.count();

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MYSQL (Кириллица тест)");
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
        assertThat(result.get(0)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(0).getDatabaseName()).isEqualTo("MYSQL (Кириллица тест)");
    }

}
