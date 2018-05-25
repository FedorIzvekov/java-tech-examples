package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JDBC;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JPA;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.SECOND_JPA;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepository;
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
    @Mock
    private MariadbJdbcRepository jdbcRepository;

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
    public void shouldInvoke_jpaFindAll() {
        when(jpaRepository.findAll()).thenReturn(asList(
                firstRow.databaseName(FIRST_JPA.name()).build(),
                secondRow.databaseName(FIRST_JPA.name()).build()
        ));

        List<String> result = databaseApiService.getDatabaseRows(FIRST_JPA.name());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).contains("TypeValue(longId=1, databaseName=FIRST_JPA, stringValue=first");
        assertThat(result.get(1)).contains("TypeValue(longId=1, databaseName=FIRST_JPA, stringValue=second");
        verify(jpaRepository).findAll();
    }


    @Test
    public void shouldInvoke_secondJpaFindAll() {
        when(secondJpaRepository.findAll()).thenReturn(asList(
                firstRow.databaseName(SECOND_JPA.name()).build(),
                secondRow.databaseName(SECOND_JPA.name()).build()
        ));

        List<String> result = databaseApiService.getDatabaseRows(SECOND_JPA.name());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).contains("TypeValue(longId=1, databaseName=SECOND_JPA, stringValue=first");
        assertThat(result.get(1)).contains("TypeValue(longId=1, databaseName=SECOND_JPA, stringValue=second");
        verify(secondJpaRepository).findAll();
    }


    @Test
    public void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(FIRST_JPA.name()).build()));

        String result = databaseApiService.getDatabaseRow(1L, FIRST_JPA.name());

        assertThat(result.toString()).contains("TypeValue(longId=1, databaseName=FIRST_JPA");
        verify(jpaRepository).findById(1L);
    }


    @Test
    public void shouldInvoke_secondJpaFindById() {
        when(secondJpaRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(SECOND_JPA.name()).build()));

        String result = databaseApiService.getDatabaseRow(1L, SECOND_JPA.name());

        assertThat(result.toString()).contains("TypeValue(longId=1, databaseName=SECOND_JPA");
        verify(secondJpaRepository).findById(1L);
    }


    @Test
    public void shouldInvoke_jdbcFindAll() {
        when(jdbcRepository.findAll()).thenReturn(asList(
                firstRow.databaseName(FIRST_JDBC.name()).build(),
                secondRow.databaseName(FIRST_JDBC.name()).build()
        ));

        List<String> result = databaseApiService.getDatabaseRows(FIRST_JDBC.name());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).contains("TypeValue(longId=1, databaseName=FIRST_JDBC, stringValue=first");
        assertThat(result.get(1)).contains("TypeValue(longId=1, databaseName=FIRST_JDBC, stringValue=second");
        verify(jdbcRepository).findAll();
    }


    @Test
    public void shouldInvoke_jdbcFindById() {
        when(jdbcRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(FIRST_JDBC.name()).build()));

        String result = databaseApiService.getDatabaseRow(1L, FIRST_JDBC.name());

        assertThat(result.toString()).contains("TypeValue(longId=1, databaseName=FIRST_JDBC");
        verify(jdbcRepository).findById(1L);
    }


    @Test
    public void shouldReturn_errorMessage() {
        List<String> result = databaseApiService.getDatabaseRows("");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).contains("QueryPerformanceJpaApp not support shard: 'UNKNOWN'");
        verify(jpaRepository, never()).findAll();
    }

}
