package com.fedorizvekov.db.mariadb.multipledatasources.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fedorizvekov.db.mariadb.multipledatasources.service.DatabaseApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MariadbController.class)
public class MariadbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;
    private String api = "first_jpa";
    private long id = 1L;


    @Test
    public void shouldInvoke_countDatabaseRows() throws Exception {
        mockMvc.perform(get("/{api}/count/rows", api))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).countDatabaseRows(eq(api));
    }


    @Test
    public void shouldInvoke_getDatabaseRow() throws Exception {
        mockMvc.perform(get("/{api}/row/{id}", api, id))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRow(eq(id), eq(api));
    }


    @Test
    public void shouldInvoke_getDatabaseRows() throws Exception {
        mockMvc.perform(get("/{api}/rows", api))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRows(eq(api));
    }

}
