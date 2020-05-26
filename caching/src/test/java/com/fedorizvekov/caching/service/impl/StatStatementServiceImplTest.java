package com.fedorizvekov.caching.service.impl;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import com.fedorizvekov.caching.repository.JdbcRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatStatementServiceImplTest {

    @InjectMocks
    private StatStatementServiceImpl statStatementService;

    @Mock
    private JdbcRepository jdbcRepository;


    @DisplayName("Should invoke findStatStatementByTableName")
    @Test
    void shouldInvoke_findStatStatementByTableName() {
        statStatementService.getStatStatementByTableName("test_table_name");
        verify(jdbcRepository).findStatStatementByTableName(eq("test_table_name"));
    }

}
