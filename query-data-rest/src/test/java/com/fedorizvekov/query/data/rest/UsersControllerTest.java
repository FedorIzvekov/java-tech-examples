package com.fedorizvekov.query.data.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedorizvekov.query.data.rest.extension.PostgresqlExtension;
import com.fedorizvekov.query.data.rest.model.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(PostgresqlExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsersControllerTest {

    private static final long ID = 1L;
    private static final String USERS_ENDPOINT = "/users";
    private static final String USERS_ID_ENDPOINT = "/users/{id}";

    @Autowired
    private MockMvc mockMvc;


    @DisplayName("Should save user")
    @Order(1)
    @Test
    void shouldSaveUser() throws Exception {

        var user = User.builder().firstName("Test").lastName("Test").build();
        var userString = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post(USERS_ENDPOINT).contentType(APPLICATION_JSON).content(userString))
                .andExpect(status().isCreated());
    }


    @DisplayName("Should read by ID")
    @Order(2)
    @Test
    void shouldReadById() throws Exception {

        mockMvc.perform(get(USERS_ID_ENDPOINT, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("Test"));
    }


    @DisplayName("Should update by ID")
    @Order(3)
    @Test
    void shouldUpdateById() throws Exception {
        var user = User.builder()
                .userId(ID)
                .firstName("newName")
                .lastName("newLastName")
                .build();

        var userAsString = new ObjectMapper().writeValueAsString(user)
                .replace("\"created\":null", "\"created\":\"1990-01-01T01:01:01.000\"");

        mockMvc.perform(put(USERS_ID_ENDPOINT, ID).contentType(APPLICATION_JSON).content(userAsString))
                .andExpect(status().isNoContent());
    }


    @DisplayName("Should read all")
    @Order(4)
    @Test
    void shouldReadAll() throws Exception {

        mockMvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.users.length()").value(1))
                .andExpect(jsonPath("$._embedded.users[0].firstName").value("newName"))
                .andExpect(jsonPath("$._embedded.users[0].lastName").value("newLastName"));
    }


    @DisplayName("Should delete by ID")
    @Test
    @Order(5)
    void shouldDeleteById() throws Exception {

        mockMvc.perform(delete(USERS_ID_ENDPOINT, ID))
                .andExpect(status().isNoContent());
    }

}
