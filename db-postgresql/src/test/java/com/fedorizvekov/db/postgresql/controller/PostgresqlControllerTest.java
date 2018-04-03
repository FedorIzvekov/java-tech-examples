package com.fedorizvekov.db.postgresql.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fedorizvekov.db.postgresql.service.DatabaseApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(PostgresqlController.class)
public class PostgresqlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;

    private long id = 1L;
    private String api = "jpa";


    @Test
    public void shouldInvoke_getDatabaseRow() throws Exception {
        mockMvc.perform(get("/" + api + "/row/" + id))
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRow(eq(1L), eq("jpa"));
    }


    @Test
    public void shouldInvoke_getDatabaseRows() throws Exception {
        mockMvc.perform(get("/" + api + "/rows"))
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRows(eq("jpa"));
    }

}
