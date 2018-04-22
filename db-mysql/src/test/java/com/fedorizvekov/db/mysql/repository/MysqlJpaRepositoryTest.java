package com.fedorizvekov.db.mysql.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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

    @Autowired
    private MysqlJpaRepository repository;


    @Test
    public void shouldCount_rows() {
        long result = repository.count();

        assertThat(result).isEqualTo(1);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MYSQL");
    }


    @Test
    public void shouldFind_allRows() {
        List<TypeValue> result = repository.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(0).getDatabaseName()).isEqualTo("MYSQL");
    }

}
