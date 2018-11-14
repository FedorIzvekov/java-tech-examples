package com.fedorizvekov.db.oracle.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fedorizvekov.db.oracle.model.enums.ApiType;
import com.fedorizvekov.db.oracle.service.DatabaseApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OracleController.class)
class OracleControllerTest {

    private static final long ID = 1L;
    private static final String COUNT_ENDPOINT = "/{api}/rows/count";
    private static final String ROW_BY_ID_ENDPOINT = "/{api}/row/{id}";
    private static final String ROWS_ENDPOINT = "/{api}/rows";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;


    @DisplayName("Should invoke countDatabaseRows")
    @EnumSource(ApiType.class)
    @ParameterizedTest
    void shouldInvoke_countDatabaseRows(ApiType api) throws Exception {
        mockMvc.perform(get(COUNT_ENDPOINT, api.name()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).countDatabaseRows(eq(api.name()));
    }


    @DisplayName("Should invoke getDatabaseRow")
    @EnumSource(ApiType.class)
    @ParameterizedTest
    void shouldInvoke_getDatabaseRow(ApiType api) throws Exception {
        mockMvc.perform(get(ROW_BY_ID_ENDPOINT, api.name(), ID))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRow(eq(ID), eq(api.name()));
    }


    @DisplayName("Should invoke getDatabaseRows")
    @EnumSource(ApiType.class)
    @ParameterizedTest
    void shouldInvoke_getDatabaseRows(ApiType api) throws Exception {
        mockMvc.perform(get(ROWS_ENDPOINT, api.name()))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRows(eq(api.name()));
    }

}
