package com.fedorizvekov.db.clickhouse.service.impl;

import static com.fedorizvekov.db.clickhouse.model.enums.ApiType.JPA;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.fedorizvekov.db.clickhouse.exception.InvalidApiTypeException;
import com.fedorizvekov.db.clickhouse.exception.NotFoundException;
import com.fedorizvekov.db.clickhouse.model.entity.TypeValue;
import com.fedorizvekov.db.clickhouse.repository.ClickhouseJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseApiServiceImplTest {

    private final long id = 1L;
    private final String apiJpa = JPA.name();
    private final TypeValue firstRow = TypeValue.builder().longId(id).databaseName("CLICKHOUSE").build();

    @InjectMocks
    private DatabaseApiServiceImpl databaseApiService;

    @Mock
    private ClickhouseJpaRepository jpaRepository;


    @Test
    public void shouldInvoke_jpaCount() {
        databaseApiService.countDatabaseRows(apiJpa);
        verify(jpaRepository).count();
    }


    @Test
    public void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(firstRow));

        databaseApiService.getDatabaseRow(id, apiJpa);

        verify(jpaRepository).findById(id);
    }


    @Test
    public void shouldInvoke_jpaFindAll() {
        databaseApiService.getDatabaseRows(apiJpa);
        verify(jpaRepository).findAll();
    }


    @Test(expected = NotFoundException.class)
    public void shouldThrow_NotFoundException() {
        when(jpaRepository.findById(anyLong())).thenReturn(Optional.empty());
        databaseApiService.getDatabaseRow(id, apiJpa);
    }


    @Test(expected = InvalidApiTypeException.class)
    public void shouldThrow_InvalidApiTypeException() {
        databaseApiService.countDatabaseRows("");
        databaseApiService.getDatabaseRows("");
        databaseApiService.getDatabaseRow(id, "");

        verify(jpaRepository, never()).count();
        verify(jpaRepository, never()).findById(anyLong());
        verify(jpaRepository, never()).findAll();
    }

}
