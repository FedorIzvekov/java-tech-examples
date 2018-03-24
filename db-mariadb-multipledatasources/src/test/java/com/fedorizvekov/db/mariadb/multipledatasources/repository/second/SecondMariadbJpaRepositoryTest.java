package com.fedorizvekov.db.mariadb.multipledatasources.repository.second;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import com.fedorizvekov.db.mariadb.multipledatasources.config.MariadbConfig;
import com.fedorizvekov.db.mariadb.multipledatasources.config.SecondMariadbConfig;
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
@ContextConfiguration(classes = {MariadbConfig.class, SecondMariadbConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SecondMariadbJpaRepositoryTest {

    @Autowired
    private SecondMariadbJpaRepository repository;


    @Test
    public void shouldCount_rows_and_ReturnOne() {
        long result = repository.count();

        assertThat(result).isEqualTo(1);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = repository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("MARIADB second shard");
    }


    @Test
    public void shouldFind_allRows() {
        List<TypeValue> result = repository.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(0).getDatabaseName()).isEqualTo("MARIADB second shard");
    }

}
