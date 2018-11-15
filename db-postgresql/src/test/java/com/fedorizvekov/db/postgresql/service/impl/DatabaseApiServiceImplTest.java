package com.fedorizvekov.db.postgresql.service.impl;

import static com.fedorizvekov.db.postgresql.model.enums.ApiType.JDBC;
import static com.fedorizvekov.db.postgresql.model.enums.ApiType.JPA;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import com.fedorizvekov.db.postgresql.exception.InvalidApiTypeException;
import com.fedorizvekov.db.postgresql.exception.NotFoundException;
import com.fedorizvekov.db.postgresql.model.entity.TypeValue;
import com.fedorizvekov.db.postgresql.repository.PostgresqlJdbcRepository;
import com.fedorizvekov.db.postgresql.repository.PostgresqlJpaRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseApiServiceImplTest {

    private static final long ID = 1L;
    private static final String API_JPA = JPA.name();
    private static final String API_JDBC = JDBC.name();

    @Mock
    private static PostgresqlJpaRepository jpaRepository;
    @Mock
    private static PostgresqlJdbcRepository jdbcRepository;

    private final TypeValue firstRow = TypeValue.builder().longId(ID).databaseName("CLICKHOUSE").build();

    @InjectMocks
    private DatabaseApiServiceImpl databaseApiService;


    private static Stream<ThrowableAssert.ThrowingCallable> provideNotFoundException() {
        var databaseApiService = new DatabaseApiServiceImpl(jpaRepository, jdbcRepository);

        return Stream.of(
                () -> databaseApiService.getDatabaseRow(ID, API_JPA),
                () -> databaseApiService.getDatabaseRow(ID, API_JDBC)
        );
    }


    private static Stream<ThrowableAssert.ThrowingCallable> provideInvalidApiTypeException() {
        var databaseApiService = new DatabaseApiServiceImpl(jpaRepository, jdbcRepository);

        return Stream.of(
                () -> databaseApiService.countDatabaseRows("invalidApi"),
                () -> databaseApiService.getDatabaseRows("invalidApi"),
                () -> databaseApiService.getDatabaseRow(ID, "invalidApi")
        );
    }


    @DisplayName("Should invoke jpa count")
    @Test
    void shouldInvoke_jpaCount() {
        databaseApiService.countDatabaseRows(API_JPA);
        verify(jpaRepository).count();
    }


    @DisplayName("Should invoke jdbc count")
    @Test
    void shouldInvoke_jdbcCount() {
        databaseApiService.countDatabaseRows(API_JDBC);
        verify(jdbcRepository).count();
    }


    @DisplayName("Should invoke jpa find by id")
    @Test
    void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(firstRow));

        databaseApiService.getDatabaseRow(ID, API_JPA);

        verify(jpaRepository).findById(ID);
    }


    @DisplayName("Should invoke jdbc find by id")
    @Test
    void shouldInvoke_jdbcFindById() {
        when(jdbcRepository.findById(anyLong())).thenReturn(of(firstRow));

        databaseApiService.getDatabaseRow(ID, API_JDBC);

        verify(jdbcRepository).findById(ID);
    }


    @DisplayName("Should invoke jpa find all")
    @Test
    void shouldInvoke_jpaFindAll() {
        databaseApiService.getDatabaseRows(API_JPA);
        verify(jpaRepository).findAll();
    }


    @DisplayName("Should invoke jdbc find all")
    @Test
    void shouldInvoke_jdbcFindAll() {
        databaseApiService.getDatabaseRows(API_JDBC);
        verify(jdbcRepository).findAll();
    }


    @DisplayName("Should throw NotFoundException")
    @MethodSource("provideNotFoundException")
    @ParameterizedTest
    void shouldThrow_NotFoundException(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found TypeValue with id '1'");
    }


    @DisplayName("Should throw InvalidApiTypeException")
    @MethodSource("provideInvalidApiTypeException")
    @ParameterizedTest
    void shouldThrow_InvalidApiTypeException(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(InvalidApiTypeException.class)
                .hasMessageContaining("Unsupported Api Type 'invalidApi', supported: [JPA, JDBC]");

        verify(jpaRepository, never()).count();
        verify(jpaRepository, never()).findById(anyLong());
        verify(jpaRepository, never()).findAll();
        verify(jdbcRepository, never()).count();
        verify(jdbcRepository, never()).findById(anyLong());
        verify(jdbcRepository, never()).findAll();
    }

}
