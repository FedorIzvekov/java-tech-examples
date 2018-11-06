package com.fedorizvekov.query.jpa.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorizvekov.query.jpa.model.entity.User;
import com.fedorizvekov.query.jpa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final long ID = 1L;
    private static final String USERS_ENDPOINT = "/users";
    private static final String USER_ID_ENDPOINT = "/user/{id}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private String userAsString;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        user = User.builder()
                .firstName("Test")
                .lastName("Test")
                .build();

        userAsString = new ObjectMapper().writeValueAsString(user);
    }


    @DisplayName("Should invoke create")
    @Test
    void shouldInvoke_create() throws Exception {

        mockMvc.perform(post(USERS_ENDPOINT).contentType(APPLICATION_JSON).content(userAsString))
                .andExpect(status().isCreated());

        verify(userService).create(eq(user));
    }


    @DisplayName("Should invoke readAll")
    @Test
    void shouldInvoke_readAll() throws Exception {

        mockMvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk());

        verify(userService).readAll();
    }


    @DisplayName("Should invoke read")
    @Test
    void shouldInvoke_read() throws Exception {

        mockMvc.perform(get(USER_ID_ENDPOINT, ID))
                .andExpect(status().isOk());

        verify(userService).read(eq(ID));
    }


    @DisplayName("Should invoke update")
    @Test
    void shouldInvoke_update() throws Exception {

        mockMvc.perform(put(USER_ID_ENDPOINT, ID).contentType(APPLICATION_JSON).content(userAsString))
                .andExpect(status().isOk());

        verify(userService).update(eq(ID), eq(user));
    }


    @DisplayName("Should invoke delete")
    @Test
    void shouldInvoke_delete() throws Exception {

        mockMvc.perform(delete(USER_ID_ENDPOINT, ID))
                .andExpect(status().isOk());

        verify(userService).delete(eq(ID));
    }

}
