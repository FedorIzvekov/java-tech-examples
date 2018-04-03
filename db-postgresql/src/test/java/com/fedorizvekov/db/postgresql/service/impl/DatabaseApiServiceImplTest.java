package com.fedorizvekov.db.postgresql.service.impl;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import com.fedorizvekov.db.postgresql.model.entity.TypeValue;
import com.fedorizvekov.db.postgresql.repository.PostgresqlJpaRepository;
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
    private PostgresqlJpaRepository jpaRepository;

    private TypeValue typeValue = TypeValue.builder()
            .longId(1L)
            .databaseName("POSTGRESQL")
            .stringValue("first")
            .build();


    @Test
    public void shouldInvoke_jpaFindById() {
        when(jpaRepository.findById(anyLong())).thenReturn(of(typeValue));

        String result = databaseApiService.getDatabaseRow(1L, "JPA");

        assertThat(result.toString()).contains("TypeValue(longId=1, databaseName=POSTGRESQL, stringValue=first");
        verify(jpaRepository).findById(1L);
    }


    @Test
    public void shouldInvoke_jpaFindAll() {
        when(jpaRepository.findAll()).thenReturn(asList(
                typeValue,
                TypeValue.builder()
                        .longId(1L)
                        .databaseName("POSTGRESQL")
                        .stringValue("second")
                        .build()
        ));

        List<String> result = databaseApiService.getDatabaseRows("JPA");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).contains("TypeValue(longId=1, databaseName=POSTGRESQL, stringValue=first");
        assertThat(result.get(1)).contains("TypeValue(longId=1, databaseName=POSTGRESQL, stringValue=second");
        verify(jpaRepository).findAll();
    }

}
