package com.fedorizvekov.statemachine.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.service.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest {

    private final Long userId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;


    @ParameterizedTest
    @EnumSource(RegistrationEvent.class)
    @DisplayName("Should invoke sendEvent")
    void shouldInvoke_sendEvent(RegistrationEvent event) throws Exception {
        mockMvc.perform(post("/registration/event/{userId}", userId).param("event", event.toString()))
                .andExpect(status().isOk());

        verify(registrationService).sendEvent(eq(userId), eq(event));
    }


    @Test
    @DisplayName("Should invoke getCurrentState")
    void shouldInvoke_getCurrentState() throws Exception {
        mockMvc.perform(get("/registration/state/{userId}", userId))
                .andExpect(status().isOk());

        verify(registrationService).getCurrentState(eq(userId));
    }

}
