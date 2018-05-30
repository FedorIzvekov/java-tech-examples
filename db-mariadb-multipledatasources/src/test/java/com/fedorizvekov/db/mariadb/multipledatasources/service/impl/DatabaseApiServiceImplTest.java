package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JDBC;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JPA;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.SECOND_JDBC;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.SECOND_JPA;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.InvalidApiTypeException;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.NotFoundException;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJpaRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.MariadbJdbcTemplateRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.SecondMariadbJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseApiServiceImplTest {

    private final long id = 1L;
    private final String apiFirstJpa = FIRST_JPA.name();
    private final String apiSecondJpa = SECOND_JPA.name();
    private final String apiFirstJdbc = FIRST_JDBC.name();
    private final String apiSecondJdbc = SECOND_JDBC.name();
    private final TypeValue.TypeValueBuilder firstRow = TypeValue.builder().longId(id);

    @InjectMocks
    private DatabaseApiServiceImpl databaseApiService;

    @Mock
    private MariadbJpaRepository jpaRepository;
    @Mock
    private SecondMariadbJpaRepository secondJpaRepository;
    @Mock
    private MariadbJdbcRepository jdbcRepository;
    @Mock
    private MariadbJdbcTemplateRepository jdbcTemplateRepository;


    @Test
    public void shouldInvoke_jpaCount() {
        databaseApiService.countDatabaseRows(apiFirstJpa);
        verify(jpaRepository).count();
    }


    @Test
    public void shouldInvoke_secondJpaCount() {
        databaseApiService.countDatabaseRows(apiSecondJpa);
        verify(secondJpaRepository).count();
    }


    @Test
    public void shouldInvoke_jpaFindAll() {
        databaseApiService.getDatabaseRows(apiFirstJpa);
        verify(jpaRepository).findAll();
    }


    @Test
    public void shouldInvoke_secondJpaFindAll() {
        databaseApiService.getDatabaseRows(apiSecondJpa);
        verify(secondJpaRepository).findAll();
    }


    @Test
    public void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(apiFirstJpa).build()));

        databaseApiService.getDatabaseRow(id, apiFirstJpa);

        verify(jpaRepository).findById(id);
    }


    @Test
    public void shouldInvoke_secondJpaFindById() {
        when(secondJpaRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(apiSecondJpa).build()));

        databaseApiService.getDatabaseRow(id, apiSecondJpa);

        verify(secondJpaRepository).findById(id);
    }


    @Test
    public void shouldInvoke_jdbcCount() {
        databaseApiService.countDatabaseRows(apiFirstJdbc);
        verify(jdbcRepository).count();
    }


    @Test
    public void shouldInvoke_jdbcTemplateCount() {
        databaseApiService.countDatabaseRows(apiSecondJdbc);
        verify(jdbcTemplateRepository).count();
    }


    @Test
    public void shouldInvoke_jdbcFindAll() {
        databaseApiService.getDatabaseRows(apiFirstJdbc);
        verify(jdbcRepository).findAll();
    }


    @Test
    public void shouldInvoke_jdbcFindById() {
        when(jdbcRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(apiFirstJdbc).build()));

        databaseApiService.getDatabaseRow(id, apiFirstJdbc);

        verify(jdbcRepository).findById(id);
    }


    @Test
    public void shouldInvoke_jdbcTemplateFindAll() {
        databaseApiService.getDatabaseRows(apiSecondJdbc);
        verify(jdbcTemplateRepository).findAll();
    }


    @Test
    public void shouldInvoke_jdbcTemplateFindById() {
        when(jdbcTemplateRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(apiSecondJdbc).build()));

        databaseApiService.getDatabaseRow(id, apiSecondJdbc);

        verify(jdbcTemplateRepository).findById(id);
    }


    @Test(expected = NotFoundException.class)
    public void shouldThrow_NotFoundException() {
        when(jpaRepository.findById(anyLong())).thenReturn(Optional.empty());
        databaseApiService.getDatabaseRow(id, apiFirstJpa);

        when(secondJpaRepository.findById(anyLong())).thenReturn(Optional.empty());
        databaseApiService.getDatabaseRow(id, apiSecondJpa);

        when(jdbcRepository.findById(anyLong())).thenReturn(Optional.empty());
        databaseApiService.getDatabaseRow(id, apiFirstJdbc);

        when(jdbcTemplateRepository.findById(anyLong())).thenReturn(Optional.empty());
        databaseApiService.getDatabaseRow(id, apiSecondJdbc);
    }


    @Test(expected = InvalidApiTypeException.class)
    public void shouldThrow_InvalidApiTypeException() {
        databaseApiService.countDatabaseRows("");
        databaseApiService.getDatabaseRows("");
        databaseApiService.getDatabaseRow(id, "");

        verify(jpaRepository, never()).count();
        verify(secondJpaRepository, never()).count();
        verify(jdbcRepository, never()).count();
        verify(jdbcTemplateRepository, never()).count();
        verify(jpaRepository, never()).findAll();
        verify(secondJpaRepository, never()).findAll();
        verify(jdbcRepository, never()).findAll();
        verify(jdbcTemplateRepository, never()).findAll();
        verify(jpaRepository, never()).findById(anyLong());
        verify(secondJpaRepository, never()).findById(anyLong());
        verify(jdbcRepository, never()).findById(anyLong());
        verify(jdbcTemplateRepository, never()).findById(anyLong());
    }

}
