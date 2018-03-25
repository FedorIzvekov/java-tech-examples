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
@WebMvcTest(DataSourceController.class)
public class DataSourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;


    @Test
    public void shouldInvoke_getDatabaseRows() throws Exception {
        mockMvc.perform(get("/{database}/rows", "first_shard"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(databaseApiService).getDatabaseRows(eq("first_shard"));
    }

}
