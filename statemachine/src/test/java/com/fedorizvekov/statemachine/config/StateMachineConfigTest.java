package com.fedorizvekov.statemachine.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    private StateMachineFactory<RegistrationState, RegistrationEvent> factory;


    @Test
    @DisplayName("Should start from INITIAL state")
    void shouldStart_fromInitialState() {
        var stateMachine = factory.getStateMachine();
        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.INITIAL);
    }


    @Test
    @DisplayName("Should return to SELECT_IDENTIFICATION_METHOD state")
    void shouldReturn_toSelectIdentificationMethodState() {
        var stateMachine = factory.getStateMachine();

        stateMachine.sendEvent(RegistrationEvent.NEW_USER_SAVED);
        stateMachine.sendEvent(RegistrationEvent.EMAIL_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.PHONE_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.SELECT_FAST_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.CANCEL_SELECTION);
        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.SELECT_IDENTIFICATION_METHOD);

        stateMachine.sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.CANCEL_SELECTION);
        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.SELECT_IDENTIFICATION_METHOD);

        stateMachine.sendEvent(RegistrationEvent.SELECT_FAST_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.SEND_DATA_REQUEST);
        stateMachine.sendEvent(RegistrationEvent.FAILED_ANSWER);
        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.SELECT_IDENTIFICATION_METHOD);
    }


    @Test
    @DisplayName("Should return to FULL_IDENTIFICATION state")
    void shouldReturn_toFullIdentificationState() {
        var stateMachine = factory.getStateMachine();

        stateMachine.sendEvent(RegistrationEvent.NEW_USER_SAVED);
        stateMachine.sendEvent(RegistrationEvent.EMAIL_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.PHONE_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.PHOTO_UPLOADED);
        stateMachine.sendEvent(RegistrationEvent.FAILED_PHOTO_VERIFICATION);

        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.FULL_IDENTIFICATION);
    }


    @Test
    @DisplayName("Should finish to COMPLETED state via SELECT_FAST_IDENTIFICATION event")
    void shouldFinish_toCompletedState_viaSelectFastIdentificationEvent() {
        var stateMachine = factory.getStateMachine();

        stateMachine.sendEvent(RegistrationEvent.NEW_USER_SAVED);
        stateMachine.sendEvent(RegistrationEvent.EMAIL_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.PHONE_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.SELECT_FAST_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.SEND_DATA_REQUEST);
        stateMachine.sendEvent(RegistrationEvent.SUCCESSFUL_ANSWER);

        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.COMPLETED);
    }


    @Test
    @DisplayName("Should finish to COMPLETED state via SELECT_FULL_IDENTIFICATION event")
    void shouldFinish_toCompletedState_viaSelectFullIdentificationEvent() {
        var stateMachine = factory.getStateMachine();

        stateMachine.sendEvent(RegistrationEvent.NEW_USER_SAVED);
        stateMachine.sendEvent(RegistrationEvent.EMAIL_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.PHONE_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.PHOTO_UPLOADED);
        stateMachine.sendEvent(RegistrationEvent.PHOTO_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.SUCCESSFUL_CHECK);

        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.COMPLETED);
    }


    @Test
    @DisplayName("Should finish to BLOCKED state")
    void shouldFinish_toBlockedState() {
        var stateMachine = factory.getStateMachine();

        stateMachine.sendEvent(RegistrationEvent.NEW_USER_SAVED);
        stateMachine.sendEvent(RegistrationEvent.EMAIL_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.PHONE_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION);
        stateMachine.sendEvent(RegistrationEvent.PHOTO_UPLOADED);
        stateMachine.sendEvent(RegistrationEvent.PHOTO_CONFIRMED);
        stateMachine.sendEvent(RegistrationEvent.FAILED_CHECK);

        assertThat(stateMachine.getState().getId()).isEqualTo(RegistrationState.BLOCKED);
    }

}
