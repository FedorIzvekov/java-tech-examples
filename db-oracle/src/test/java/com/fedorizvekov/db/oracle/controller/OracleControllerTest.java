package com.fedorizvekov.db.oracle.controller;

import static com.fedorizvekov.db.oracle.model.enums.ApiType.JPA;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fedorizvekov.db.oracle.service.DatabaseApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(OracleController.class)
public class OracleControllerTest {

    public static final String COUNT_ENDPOINT = "/{api}/rows/count";
    public static final String ROW_BY_ID_ENDPOINT = "/{api}/row/{id}";
    public static final String ROWS_ENDPOINT = "/{api}/rows";
    public static final long ID = 1L;

    private final String api = JPA.name();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;


    @Test
    public void shouldInvoke_countDatabaseRows() throws Exception {
        mockMvc.perform(get(COUNT_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).countDatabaseRows(eq(api));
    }


    @Test
    public void shouldInvoke_getDatabaseRow() throws Exception {
        mockMvc.perform(get(ROW_BY_ID_ENDPOINT, api, ID))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRow(eq(ID), eq(api));
    }


    @Test
    public void shouldInvoke_getDatabaseRows() throws Exception {
        mockMvc.perform(get(ROWS_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRows(eq(api));
    }

}
