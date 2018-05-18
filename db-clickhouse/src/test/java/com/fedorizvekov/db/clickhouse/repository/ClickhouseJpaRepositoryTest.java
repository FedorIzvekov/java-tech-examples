package com.fedorizvekov.db.clickhouse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import com.fedorizvekov.db.clickhouse.model.entity.TypeValue;
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
public class ClickhouseJpaRepositoryTest {

    @Autowired
    private ClickhouseJpaRepository jpaRepository;


    @Test
    public void shouldCount_rows() {
        long result = jpaRepository.count();

        assertThat(result).isEqualTo(1);
    }


    @Test
    public void shouldFind_rowById() {
        TypeValue result = jpaRepository.findById(1L).get();

        assertThat(result).isInstanceOfAny(TypeValue.class);
        assertThat(result.getDatabaseName()).isEqualTo("CLICKHOUSE");
    }


    @Test
    public void shouldFind_allRows() {
        List<TypeValue> result = jpaRepository.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isInstanceOfAny(TypeValue.class);
        assertThat(result.get(0).getDatabaseName()).isEqualTo("CLICKHOUSE");
    }

}
