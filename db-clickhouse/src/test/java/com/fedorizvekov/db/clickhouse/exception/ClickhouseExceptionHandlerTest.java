package com.fedorizvekov.db.clickhouse.exception;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.stream.Stream;
import javax.persistence.PersistenceException;
import com.fedorizvekov.db.clickhouse.model.enums.ApiType;
import com.fedorizvekov.db.clickhouse.service.DatabaseApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@WebMvcTest
class ClickhouseExceptionHandlerTest {

    private static final long ID = 1L;
    private static final String api = "test";
    private static final String COUNT_ENDPOINT = "/{api}/rows/count";
    private static final String ROW_BY_ID_ENDPOINT = "/{api}/row/{id}";
    private static final String ROWS_ENDPOINT = "/{api}/rows";
    private static final RequestBuilder COUNT = get(COUNT_ENDPOINT, api);
    private static final RequestBuilder READ_ALL = get(ROWS_ENDPOINT, api);
    private static final RequestBuilder READ_BY_ID = get(ROW_BY_ID_ENDPOINT, api, ID);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseApiService databaseApiService;


    private static Stream<Arguments> provideException() {
        return Stream.of(COUNT, READ_BY_ID, READ_ALL).map(Arguments::of);
    }


    @DisplayName("Should handle InvalidApiTypeException")
    @MethodSource("provideException")
    @ParameterizedTest
    void shouldHandle_InvalidApiTypeException(RequestBuilder requestBuilder) throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: InvalidApiTypeException: Unsupported Api Type 'test', supported: " + Arrays.toString(ApiType.values());
        var doThrow = doThrow(new InvalidApiTypeException("Unsupported Api Type 'test', supported: " + Arrays.toString(ApiType.values())));

        doThrow.when(databaseApiService).countDatabaseRows(anyString());
        doThrow.when(databaseApiService).getDatabaseRow(anyLong(), anyString());
        doThrow.when(databaseApiService).getDatabaseRows(anyString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(400))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @DisplayName("Should handle NotFoundException")
    @Test
    void shouldHandle_NotFoundException() throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: NotFoundException: Not found TypeValue with id '1'";
        when(databaseApiService.getDatabaseRow(anyLong(), anyString()))
                .thenThrow(new NotFoundException("Not found TypeValue with id '1'"));

        mockMvc.perform(READ_BY_ID)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value(404))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @DisplayName("Should handle PersistenceException")
    @MethodSource("provideException")
    @ParameterizedTest
    void shouldHandle_PersistenceException(RequestBuilder requestBuilder) throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: DataBaseException: Database error";
        var doThrow = doThrow(new PersistenceException("Database error"));

        doThrow.when(databaseApiService).countDatabaseRows(anyString());
        doThrow.when(databaseApiService).getDatabaseRow(anyLong(), anyString());
        doThrow.when(databaseApiService).getDatabaseRows(anyString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @DisplayName("Should handle other Exception")
    @MethodSource("provideException")
    @ParameterizedTest
    void shouldHandle_OtherException(RequestBuilder requestBuilder) throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: Exception: Something went wrong, because: Other error";
        var doThrow = doThrow(new RuntimeException("Other error"));

        doThrow.when(databaseApiService).countDatabaseRows(anyString());
        doThrow.when(databaseApiService).getDatabaseRow(anyLong(), anyString());
        doThrow.when(databaseApiService).getDatabaseRows(anyString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }

}
