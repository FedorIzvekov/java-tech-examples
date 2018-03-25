package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.Shard.FIRST_SHARD;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.Shard.SECOND_SHARD;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJpaRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.SecondMariadbJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseApiServiceImplTest {

    @InjectMocks
    private DatabaseApiServiceImpl databaseApiService;

    @Mock
    private MariadbJpaRepository jpaRepository;
    @Mock
    private SecondMariadbJpaRepository secondJpaRepository;

    private TypeValue.TypeValueBuilder firstRow;
    private TypeValue.TypeValueBuilder secondRow;


    @Before
    public void setUp() {
        firstRow = TypeValue.builder()
                .longId(1L)
                .stringValue("first");

        secondRow = TypeValue.builder()
                .longId(1L)
                .stringValue("second");
    }


    @Test
    public void shouldInvoke_mariadbJpaRepository() {
        when(jpaRepository.findAll()).thenReturn(asList(
                firstRow.databaseName(FIRST_SHARD.name()).build(),
                secondRow.databaseName(FIRST_SHARD.name()).build()
        ));

        List<String> result = databaseApiService.getDatabaseRows("first_shard");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).contains("TypeValue(longId=1, databaseName=FIRST_SHARD, stringValue=first");
        assertThat(result.get(1)).contains("TypeValue(longId=1, databaseName=FIRST_SHARD, stringValue=second");
        verify(jpaRepository).findAll();
    }


    @Test
    public void shouldInvoke_secondMariadbJpaRepository() {
        when(secondJpaRepository.findAll()).thenReturn(asList(
                firstRow.databaseName(SECOND_SHARD.name()).build(),
                secondRow.databaseName(SECOND_SHARD.name()).build()
        ));

        List<String> result = databaseApiService.getDatabaseRows("second_shard");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).contains("TypeValue(longId=1, databaseName=SECOND_SHARD, stringValue=first");
        assertThat(result.get(1)).contains("TypeValue(longId=1, databaseName=SECOND_SHARD, stringValue=second");
        verify(secondJpaRepository).findAll();
    }


    @Test
    public void shouldReturn_errorMessage() {
        List<String> result = databaseApiService.getDatabaseRows("");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).contains("QueryPerformanceJpaApp not support shard: 'UNKNOWN'");
        verify(jpaRepository, never()).findAll();
    }

}
