package com.fedorizvekov.statemachine.config;

import static com.fedorizvekov.statemachine.model.enums.RegistrationEvent.*;
import static com.fedorizvekov.statemachine.model.enums.RegistrationState.*;

import java.util.EnumSet;
import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<RegistrationState, RegistrationEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<RegistrationState, RegistrationEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }


    @Override
    public void configure(StateMachineStateConfigurer<RegistrationState, RegistrationEvent> states) throws Exception {
        states.withStates()
                .initial(INITIAL)
                .end(COMPLETED)
                .end(BLOCKED)
                .states(EnumSet.allOf(RegistrationState.class));
    }


    @Override
    public void configure(StateMachineTransitionConfigurer<RegistrationState, RegistrationEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(INITIAL)
                .target(EMAIL_VERIFICATION)
                .event(NEW_USER_SAVED)

                .and().withExternal()
                .source(EMAIL_VERIFICATION)
                .target(PHONE_VERIFICATION)
                .event(EMAIL_CONFIRMED)

                .and().withExternal()
                .source(PHONE_VERIFICATION)
                .target(SELECT_IDENTIFICATION_METHOD)
                .event(PHONE_CONFIRMED)

                .and().withExternal()
                .source(SELECT_IDENTIFICATION_METHOD)
                .target(FAST_IDENTIFICATION)
                .event(SELECT_FAST_IDENTIFICATION)

                .and().withExternal()
                .source(SELECT_IDENTIFICATION_METHOD)
                .target(FULL_IDENTIFICATION)
                .event(SELECT_FULL_IDENTIFICATION)

                .and().withExternal()
                .source(FAST_IDENTIFICATION)
                .target(SELECT_IDENTIFICATION_METHOD)
                .event(CANCEL_SELECTION)

                .and().withExternal()
                .source(FAST_IDENTIFICATION)
                .target(DATA_REQUESTED)
                .event(SEND_DATA_REQUEST)

                .and().withExternal()
                .source(DATA_REQUESTED)
                .target(SELECT_IDENTIFICATION_METHOD)
                .event(FAILED_ANSWER)

                .and().withExternal()
                .source(DATA_REQUESTED)
                .target(COMPLETED)
                .event(SUCCESSFUL_ANSWER)

                .and().withExternal()
                .source(FULL_IDENTIFICATION)
                .target(SELECT_IDENTIFICATION_METHOD)
                .event(CANCEL_SELECTION)

                .and().withExternal()
                .source(FULL_IDENTIFICATION)
                .target(PHOTO_VERIFICATION)
                .event(PHOTO_UPLOADED)

                .and().withExternal()
                .source(PHOTO_VERIFICATION)
                .target(FULL_IDENTIFICATION)
                .event(FAILED_PHOTO_VERIFICATION)

                .and().withExternal()
                .source(PHOTO_VERIFICATION)
                .target(BLACKLIST_CHECKING)
                .event(PHOTO_CONFIRMED)

                .and().withExternal()
                .source(BLACKLIST_CHECKING)
                .target(COMPLETED)
                .event(SUCCESSFUL_CHECK)

                .and().withExternal()
                .source(BLACKLIST_CHECKING)
                .target(BLOCKED)
                .event(FAILED_CHECK);
    }

}
