package com.fedorizvekov.statemachine.service.impl;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Log4j2
public class EmailConfirmationAction implements Action<RegistrationState, RegistrationEvent> {

    @Override
    public void execute(StateContext<RegistrationState, RegistrationEvent> stateContext) {

        if (stateContext.getTarget().getId() == RegistrationState.EMAIL_VERIFICATION) {
            log.info("Send email confirmation code");
        }

        stateContext.getExtendedState().getVariables().put("test", "add additional data");

    }

}
