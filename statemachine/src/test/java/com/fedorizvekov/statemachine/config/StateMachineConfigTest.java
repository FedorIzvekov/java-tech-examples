package com.fedorizvekov.statemachine.config;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    private StateMachineFactory<RegistrationState, RegistrationEvent> factory;


    @Test
    @DisplayName("Should start from INITIAL state")
    void shouldStart_fromInitialState() throws Exception {
        var stateMachine = factory.getStateMachine();

        var testPlan = StateMachineTestPlanBuilder.<RegistrationState, RegistrationEvent>builder()
                .stateMachine(stateMachine)
                .step()
                .expectStates(RegistrationState.INITIAL)
                .expectStateChanged(0)
                .and().build();

        testPlan.test();
    }


    @Test
    @DisplayName("Should return to SELECT_IDENTIFICATION_METHOD state")
    void shouldReturn_toSelectIdentificationMethodState() throws Exception {
        var stateMachine = factory.getStateMachine();

        var testPlan = StateMachineTestPlanBuilder.<RegistrationState, RegistrationEvent>builder()
                .stateMachine(stateMachine)
                .step()
                .expectStates(RegistrationState.INITIAL)
                .expectStateChanged(0)

                .and().step()
                .sendEvent(RegistrationEvent.NEW_USER_SAVED)
                .expectState(RegistrationState.EMAIL_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.EMAIL_CONFIRMED)
                .expectState(RegistrationState.PHONE_VERIFICATION)
                .expectStateChanged(1)
                .expectVariable("test", "add additional data")

                .and().step()
                .sendEvent(RegistrationEvent.PHONE_CONFIRMED)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FAST_IDENTIFICATION)
                .expectState(RegistrationState.FAST_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.CANCEL_SELECTION)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION)
                .expectState(RegistrationState.FULL_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.CANCEL_SELECTION)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FAST_IDENTIFICATION)
                .expectState(RegistrationState.FAST_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SEND_DATA_REQUEST)
                .expectState(RegistrationState.DATA_REQUESTED)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.FAILED_ANSWER)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().build();

        testPlan.test();
    }


    @Test
    @DisplayName("Should return to FULL_IDENTIFICATION state")
    void shouldReturn_toFullIdentificationState() throws Exception {
        var stateMachine = factory.getStateMachine();

        var testPlan = StateMachineTestPlanBuilder.<RegistrationState, RegistrationEvent>builder()
                .stateMachine(stateMachine)
                .step()
                .expectStates(RegistrationState.INITIAL)
                .expectStateChanged(0)

                .and().step()
                .sendEvent(RegistrationEvent.NEW_USER_SAVED)
                .expectState(RegistrationState.EMAIL_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.EMAIL_CONFIRMED)
                .expectState(RegistrationState.PHONE_VERIFICATION)
                .expectStateChanged(1)
                .expectVariable("test", "add additional data")

                .and().step()
                .sendEvent(RegistrationEvent.PHONE_CONFIRMED)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION)
                .expectState(RegistrationState.FULL_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.PHOTO_UPLOADED)
                .expectState(RegistrationState.PHOTO_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.FAILED_PHOTO_VERIFICATION)
                .expectState(RegistrationState.FULL_IDENTIFICATION)
                .expectStateChanged(1)

                .and().build();

        testPlan.test();
    }


    @Test
    @DisplayName("Should finish to COMPLETED state via SELECT_FAST_IDENTIFICATION event")
    void shouldFinish_toCompletedState_viaSelectFastIdentificationEvent() throws Exception {
        var stateMachine = factory.getStateMachine();

        var testPlan = StateMachineTestPlanBuilder.<RegistrationState, RegistrationEvent>builder()
                .stateMachine(stateMachine)
                .step()
                .expectStates(RegistrationState.INITIAL)
                .expectStateChanged(0)

                .and().step()
                .sendEvent(RegistrationEvent.NEW_USER_SAVED)
                .expectState(RegistrationState.EMAIL_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.EMAIL_CONFIRMED)
                .expectState(RegistrationState.PHONE_VERIFICATION)
                .expectStateChanged(1)
                .expectVariable("test", "add additional data")

                .and().step()
                .sendEvent(RegistrationEvent.PHONE_CONFIRMED)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FAST_IDENTIFICATION)
                .expectState(RegistrationState.FAST_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SEND_DATA_REQUEST)
                .expectState(RegistrationState.DATA_REQUESTED)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SUCCESSFUL_ANSWER)
                .expectState(RegistrationState.COMPLETED)
                .expectStateChanged(1)

                .and().build();

        testPlan.test();
    }


    @Test
    @DisplayName("Should finish to COMPLETED state via SELECT_FULL_IDENTIFICATION event")
    void shouldFinish_toCompletedState_viaSelectFullIdentificationEvent() throws Exception {
        var stateMachine = factory.getStateMachine();

        var testPlan = StateMachineTestPlanBuilder.<RegistrationState, RegistrationEvent>builder()
                .stateMachine(stateMachine)
                .step()
                .expectStates(RegistrationState.INITIAL)
                .expectStateChanged(0)

                .and().step()
                .sendEvent(RegistrationEvent.NEW_USER_SAVED)
                .expectState(RegistrationState.EMAIL_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.EMAIL_CONFIRMED)
                .expectState(RegistrationState.PHONE_VERIFICATION)
                .expectStateChanged(1)
                .expectVariable("test", "add additional data")

                .and().step()
                .sendEvent(RegistrationEvent.PHONE_CONFIRMED)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION)
                .expectState(RegistrationState.FULL_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.PHOTO_UPLOADED)
                .expectState(RegistrationState.PHOTO_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.PHOTO_CONFIRMED)
                .expectState(RegistrationState.BLACKLIST_CHECKING)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SUCCESSFUL_CHECK)
                .expectState(RegistrationState.COMPLETED)
                .expectStateChanged(1)

                .and().build();

        testPlan.test();
    }


    @Test
    @DisplayName("Should finish to BLOCKED state")
    void shouldFinish_toBlockedState() throws Exception {
        var stateMachine = factory.getStateMachine();

        var testPlan = StateMachineTestPlanBuilder.<RegistrationState, RegistrationEvent>builder()
                .stateMachine(stateMachine)
                .step()
                .expectStates(RegistrationState.INITIAL)
                .expectStateChanged(0)

                .and().step()
                .sendEvent(RegistrationEvent.NEW_USER_SAVED)
                .expectState(RegistrationState.EMAIL_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.EMAIL_CONFIRMED)
                .expectState(RegistrationState.PHONE_VERIFICATION)
                .expectStateChanged(1)
                .expectVariable("test", "add additional data")

                .and().step()
                .sendEvent(RegistrationEvent.PHONE_CONFIRMED)
                .expectState(RegistrationState.SELECT_IDENTIFICATION_METHOD)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.SELECT_FULL_IDENTIFICATION)
                .expectState(RegistrationState.FULL_IDENTIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.PHOTO_UPLOADED)
                .expectState(RegistrationState.PHOTO_VERIFICATION)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.PHOTO_CONFIRMED)
                .expectState(RegistrationState.BLACKLIST_CHECKING)
                .expectStateChanged(1)

                .and().step()
                .sendEvent(RegistrationEvent.FAILED_CHECK)
                .expectState(RegistrationState.BLOCKED)
                .expectStateChanged(1)

                .and().build();

        testPlan.test();
    }

}
