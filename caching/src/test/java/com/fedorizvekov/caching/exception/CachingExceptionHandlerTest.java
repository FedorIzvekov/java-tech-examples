package com.fedorizvekov.caching.exception;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;
import javax.persistence.PersistenceException;
import com.fedorizvekov.caching.service.DataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@WebMvcTest
class CachingExceptionHandlerTest {

    private static final long ID = 1L;
    private static final RequestBuilder READ_BY_ID = get("/by/{id}/from/{cacheType}", ID, "simple");
    private static final RequestBuilder READ_ALL = get("/all/from/{cacheType}", "simple");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;

    private static Stream<Arguments> provideException() {
        return Stream.of(READ_BY_ID, READ_ALL).map(Arguments::of);
    }


    @DisplayName("Should handle NotFoundException")
    @Test
    void shouldHandle_NotFoundException() throws Exception {
        when(dataService.findById(any(CacheType.class), anyLong()))
                .thenThrow(new NotFoundException("Not found TypeValue with id '1'"));

        mockMvc.perform(READ_BY_ID)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value(404))
                .andExpect(jsonPath("$.errorMessage")
                        .value("RESPONSE ERROR, because: NotFoundException: Not found TypeValue with id '1'"));
    }


    @DisplayName("Should handle PersistenceException")
    @MethodSource("provideException")
    @ParameterizedTest
    void shouldHandle_PersistenceException(RequestBuilder requestBuilder) throws Exception {
        var doThrow = doThrow(new PersistenceException("Database error"));

        doThrow.when(dataService).findById(any(CacheType.class), anyLong());
        doThrow.when(dataService).findAll(any(CacheType.class));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage")
                        .value("RESPONSE ERROR, because: DataBaseException: Database error"));
    }


    @DisplayName("Should handle other Exception")
    @MethodSource("provideException")
    @ParameterizedTest
    void shouldHandle_OtherException(RequestBuilder requestBuilder) throws Exception {
        var doThrow = doThrow(new RuntimeException("Other error"));

        doThrow.when(dataService).findById(any(CacheType.class), anyLong());
        doThrow.when(dataService).findAll(any(CacheType.class));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage")
                        .value("RESPONSE ERROR, because: Exception: Something went wrong, because: Other error"));
    }

}
