package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JDBC;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JPA;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.SECOND_JDBC;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.SECOND_JPA;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.InvalidApiTypeException;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.NotFoundException;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJpaRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.MariadbJdbcTemplateRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.SecondMariadbJpaRepository;
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
    private static final String API_FIRST_JPA = FIRST_JPA.name();
    private static final String API_SECOND_JPA = SECOND_JPA.name();
    private static final String API_FIRST_JDBC = FIRST_JDBC.name();
    private static final String API_SECOND_JDBC = SECOND_JDBC.name();

    @Mock
    private static MariadbJpaRepository jpaRepository;
    @Mock
    private static SecondMariadbJpaRepository secondJpaRepository;
    @Mock
    private static MariadbJdbcRepository jdbcRepository;
    @Mock
    private static MariadbJdbcTemplateRepository jdbcTemplateRepository;

    private final TypeValue.TypeValueBuilder firstRow = TypeValue.builder().longId(ID);

    @InjectMocks
    private DatabaseApiServiceImpl databaseApiService;


    private static Stream<ThrowableAssert.ThrowingCallable> provideNotFoundException() {
        var databaseApiService = new DatabaseApiServiceImpl(jpaRepository, secondJpaRepository, jdbcRepository, jdbcTemplateRepository);

        return Stream.of(
                () -> databaseApiService.getDatabaseRow(ID, API_FIRST_JPA),
                () -> databaseApiService.getDatabaseRow(ID, API_SECOND_JPA),
                () -> databaseApiService.getDatabaseRow(ID, API_FIRST_JDBC),
                () -> databaseApiService.getDatabaseRow(ID, API_SECOND_JDBC)
        );
    }


    private static Stream<ThrowableAssert.ThrowingCallable> provideInvalidApiTypeException() {
        var databaseApiService = new DatabaseApiServiceImpl(jpaRepository, secondJpaRepository, jdbcRepository, jdbcTemplateRepository);

        return Stream.of(
                () -> databaseApiService.countDatabaseRows("invalidApi"),
                () -> databaseApiService.getDatabaseRows("invalidApi"),
                () -> databaseApiService.getDatabaseRow(ID, "invalidApi")
        );
    }


    @Test
    @DisplayName("Should invoke jpa count")
    void shouldInvoke_jpaCount() {
        databaseApiService.countDatabaseRows(API_FIRST_JPA);
        verify(jpaRepository).count();
    }


    @Test
    @DisplayName("Should invoke second jpa count")
    void shouldInvoke_secondJpaCount() {
        databaseApiService.countDatabaseRows(API_SECOND_JPA);
        verify(secondJpaRepository).count();
    }


    @Test
    @DisplayName("Should invoke jdbc count")
    void shouldInvoke_jdbcCount() {
        databaseApiService.countDatabaseRows(API_FIRST_JDBC);
        verify(jdbcRepository).count();
    }


    @Test
    @DisplayName("Should invoke jdbc template count")
    void shouldInvoke_secondJdbcCount() {
        databaseApiService.countDatabaseRows(API_SECOND_JDBC);
        verify(jdbcTemplateRepository).count();
    }


    @Test
    @DisplayName("Should invoke jpa find by id")
    void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(API_FIRST_JPA).build()));

        databaseApiService.getDatabaseRow(ID, API_FIRST_JPA);

        verify(jpaRepository).findById(ID);
    }


    @Test
    @DisplayName("Should invoke second jpa find by id")
    void shouldInvoke_secondJpaFindById() {
        when(secondJpaRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(API_SECOND_JPA).build()));

        databaseApiService.getDatabaseRow(ID, API_SECOND_JPA);

        verify(secondJpaRepository).findById(ID);
    }


    @Test
    @DisplayName("Should invoke jdbc find by id")
    void shouldInvoke_jdbcFindById() {
        when(jdbcRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(API_FIRST_JDBC).build()));

        databaseApiService.getDatabaseRow(ID, API_FIRST_JDBC);

        verify(jdbcRepository).findById(ID);
    }


    @Test
    @DisplayName("Should invoke jdbc template find by id")
    void shouldInvoke_jdbcTemplateFindById() {
        when(jdbcTemplateRepository.findById(anyLong())).thenReturn(of(firstRow.databaseName(API_SECOND_JDBC).build()));

        databaseApiService.getDatabaseRow(ID, API_SECOND_JDBC);

        verify(jdbcTemplateRepository).findById(ID);
    }


    @Test
    @DisplayName("Should invoke jpa find all")
    void shouldInvoke_jpaFindAll() {
        databaseApiService.getDatabaseRows(API_FIRST_JPA);
        verify(jpaRepository).findAll();
    }


    @Test
    @DisplayName("Should invoke second jpa find all")
    void shouldInvoke_secondJpaFindAll() {
        databaseApiService.getDatabaseRows(API_SECOND_JPA);
        verify(secondJpaRepository).findAll();
    }


    @Test
    @DisplayName("Should invoke jdbc find all")
    void shouldInvoke_jdbcFindAll() {
        databaseApiService.getDatabaseRows(API_FIRST_JDBC);
        verify(jdbcRepository).findAll();
    }


    @Test
    @DisplayName("Should invoke jdbc template find all")
    void shouldInvoke_jdbcTemplateFindAll() {
        databaseApiService.getDatabaseRows(API_SECOND_JDBC);
        verify(jdbcTemplateRepository).findAll();
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
                .hasMessageContaining("Unsupported Api Type 'invalidApi', supported: [FIRST_JPA, SECOND_JPA, FIRST_JDBC, SECOND_JDBC]");

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
