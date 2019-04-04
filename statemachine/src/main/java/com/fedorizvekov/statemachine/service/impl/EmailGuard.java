package com.fedorizvekov.statemachine.service.impl;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

@Log4j2
public class EmailGuard implements Guard<RegistrationState, RegistrationEvent> {

    @Override
    public boolean evaluate(StateContext<RegistrationState, RegistrationEvent> stateContext) {
        log.info("Email confirmed");
        return true;
    }

}
