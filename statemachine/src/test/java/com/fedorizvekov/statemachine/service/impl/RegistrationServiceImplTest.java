package com.fedorizvekov.statemachine.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    private final Long userId = 1L;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private StateMachineFactory<RegistrationState, RegistrationEvent> factory;
    @Mock
    private StateMachine<RegistrationState, RegistrationEvent> stateMachine;
    @Mock
    private State<RegistrationState, RegistrationEvent> state;


    @Test
    @DisplayName("Should return current state message")
    void shouldReturn_currentStateMessage() {
        when(factory.getStateMachine(anyString())).thenReturn(stateMachine);
        when(stateMachine.getState()).thenReturn(state);
        when(state.getId()).thenReturn(RegistrationState.INITIAL);

        String result = registrationService.getCurrentState(userId);

        assertThat(result).isEqualTo("User with id = '1' has current state: INITIAL");
    }


    @Test
    @DisplayName("Should invoke sendEvent and return current state message")
    void ShouldInvoke_sendEvent_andReturn_currentStateMessage() {
        when(factory.getStateMachine(anyString())).thenReturn(stateMachine);
        when(stateMachine.getState()).thenReturn(state);
        when(state.getId()).thenReturn(RegistrationState.EMAIL_VERIFICATION);

        String state = registrationService.sendEvent(userId, RegistrationEvent.NEW_USER_SAVED);

        assertThat(state).isEqualTo("User with id = '1' has current state: EMAIL_VERIFICATION");
        verify(stateMachine).sendEvent(RegistrationEvent.NEW_USER_SAVED);
    }

}
