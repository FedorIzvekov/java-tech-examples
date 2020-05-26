package com.fedorizvekov.caching.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fedorizvekov.caching.service.DataService;
import com.fedorizvekov.caching.service.StatStatementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CachingController.class)
class CachingControllerTest {

    private static final long ID = 1L;
    private static final String GET_BY_ID_ENDPOINT = "/by/{id}/from/{cacheType}";
    private static final String GET_ALL_ENDPOINT = "/all/from/{cacheType}";
    private static final String GET_STAT_STATEMENTS = "/stat/statements/by/{tableName}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;
    @MockBean
    private StatStatementService statStatementService;


    @DisplayName("Should invoke findAll")
    @EnumSource(CacheType.class)
    @ParameterizedTest
    void shouldInvoke_findAll(CacheType cacheType) throws Exception {
        mockMvc.perform(get(GET_ALL_ENDPOINT, cacheType.name()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(dataService).findAll(eq(cacheType));
    }


    @DisplayName("Should invoke findById")
    @EnumSource(CacheType.class)
    @ParameterizedTest
    void shouldInvoke_findById(CacheType cacheType) throws Exception {
        mockMvc.perform(get(GET_BY_ID_ENDPOINT, ID, cacheType.name()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(dataService).findById(eq(cacheType), eq(ID));
    }


    @DisplayName("Should invoke getStatStatementByTableName")
    @ValueSource(strings = {"test"})
    @ParameterizedTest
    void shouldInvoke_getStatStatementByTableName(String tableName) throws Exception {
        mockMvc.perform(get(GET_STAT_STATEMENTS, tableName))
                .andDo(print())
                .andExpect(status().isOk());

        verify(statStatementService).getStatStatementByTableName(eq(tableName));
    }

}
