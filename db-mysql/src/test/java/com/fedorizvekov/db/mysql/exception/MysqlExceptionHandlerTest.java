package com.fedorizvekov.db.mysql.exception;

import static com.fedorizvekov.db.mysql.controller.MysqlControllerTest.COUNT_ENDPOINT;
import static com.fedorizvekov.db.mysql.controller.MysqlControllerTest.ID;
import static com.fedorizvekov.db.mysql.controller.MysqlControllerTest.ROWS_ENDPOINT;
import static com.fedorizvekov.db.mysql.controller.MysqlControllerTest.ROW_BY_ID_ENDPOINT;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import javax.persistence.PersistenceException;
import com.fedorizvekov.db.mysql.model.enums.ApiType;
import com.fedorizvekov.db.mysql.service.DatabaseApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MysqlExceptionHandlerTest {

    private final String api = "test";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;


    @Test
    public void shouldHandle_InvalidApiTypeException() throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: InvalidApiTypeException: Unsupported Api Type 'test', supported: " + Arrays.toString(ApiType.values());
        var doThrow = doThrow(new InvalidApiTypeException("Unsupported Api Type 'test', supported: " + Arrays.toString(ApiType.values())));

        doThrow.when(databaseApiService).countDatabaseRows(anyString());
        mockMvc.perform(get(COUNT_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(400))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));

        doThrow.when(databaseApiService).getDatabaseRow(anyLong(), anyString());
        mockMvc.perform(get(ROW_BY_ID_ENDPOINT, api, ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(400))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));

        doThrow.when(databaseApiService).getDatabaseRows(anyString());
        mockMvc.perform(get(ROWS_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(400))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @Test
    public void shouldHandle_NotFoundException() throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: NotFoundException: Not found TypeValue with id '1'";
        when(databaseApiService.getDatabaseRow(anyLong(), anyString())).thenThrow(new NotFoundException("Not found TypeValue with id '1'"));

        mockMvc.perform(get(ROW_BY_ID_ENDPOINT, api, ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value(404))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @Test
    public void shouldHandle_PersistenceException() throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: DataBaseException: Database error";
        var doThrow = doThrow(new PersistenceException("Database error"));

        doThrow.when(databaseApiService).countDatabaseRows(anyString());
        mockMvc.perform(get(COUNT_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));

        doThrow.when(databaseApiService).getDatabaseRow(anyLong(), anyString());
        mockMvc.perform(get(ROW_BY_ID_ENDPOINT, api, ID))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));

        doThrow.when(databaseApiService).getDatabaseRows(anyString());
        mockMvc.perform(get(ROWS_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @Test
    public void shouldHandle_OtherException() throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: Exception: Something went wrong, because: Other error";
        var doThrow = doThrow(new RuntimeException("Other error"));

        doThrow.when(databaseApiService).countDatabaseRows(anyString());
        mockMvc.perform(get(COUNT_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));

        doThrow.when(databaseApiService).getDatabaseRow(anyLong(), anyString());
        mockMvc.perform(get(ROW_BY_ID_ENDPOINT, api, ID))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));

        doThrow.when(databaseApiService).getDatabaseRows(anyString());
        mockMvc.perform(get(ROWS_ENDPOINT, api))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }

}
