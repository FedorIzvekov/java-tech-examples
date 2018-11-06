package com.fedorizvekov.query.jpa.exception;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;
import javax.persistence.PersistenceException;
import com.fedorizvekov.query.jpa.controller.UserController;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@WebMvcTest(UserController.class)
class UserExceptionHandlerTest {

    private static final long ID = 1L;
    private static final String USERS_ENDPOINT = "/users";
    private static final String USER_ID_ENDPOINT = "/user/{id}";
    private static final RequestBuilder CREATE = post(USERS_ENDPOINT).contentType(APPLICATION_JSON).content("{}");
    private static final RequestBuilder READ = get(USER_ID_ENDPOINT, ID);
    private static final RequestBuilder READ_ALL = get(USERS_ENDPOINT);
    private static final RequestBuilder UPDATE = put(USER_ID_ENDPOINT, ID).contentType(APPLICATION_JSON).content("{}");
    private static final RequestBuilder DELETE = delete(USER_ID_ENDPOINT, ID);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static Stream<Arguments> provideNotFoundException() {
        return Stream.of(READ, UPDATE).map(Arguments::of);
    }

    private static Stream<Arguments> provideException() {
        return Stream.of(CREATE, READ, READ_ALL, UPDATE, DELETE).map(Arguments::of);
    }


    @DisplayName("Should handle NotFoundException")
    @MethodSource("provideNotFoundException")
    @ParameterizedTest
    void shouldHandle_NotFoundException(RequestBuilder requestBuilder) throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: NotFoundException: Not found TypeValue with id '1'";
        var doThrow = doThrow(new NotFoundException("Not found TypeValue with id '1'"));

        doThrow.when(userService).read(anyLong());
        doThrow.when(userService).update(anyLong(), any(User.class));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value(404))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }


    @DisplayName("Should handle PersistenceException")
    @MethodSource("provideException")
    @ParameterizedTest
    void shouldHandle_PersistenceException(RequestBuilder requestBuilder) throws Exception {
        var exceptionMsg = "RESPONSE ERROR, because: PersistenceException: Database error";
        var doThrow = doThrow(new PersistenceException("Database error"));

        doThrow.when(userService).create(any(User.class));
        doThrow.when(userService).readAll();
        doThrow.when(userService).read(anyLong());
        doThrow.when(userService).update(anyLong(), any(User.class));
        doThrow.when(userService).delete(anyLong());

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

        doThrow.when(userService).create(any(User.class));
        doThrow.when(userService).readAll();
        doThrow.when(userService).read(anyLong());
        doThrow.when(userService).update(anyLong(), any(User.class));
        doThrow.when(userService).delete(anyLong());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(500))
                .andExpect(jsonPath("$.errorMessage").value(exceptionMsg));
    }

}
