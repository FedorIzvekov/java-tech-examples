package com.fedorizvekov.statemachine.service.impl;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Log4j2
public class PhoneConfirmationAction implements Action<RegistrationState, RegistrationEvent> {

    @Override
    public void execute(StateContext<RegistrationState, RegistrationEvent> stateContext) {

        if (stateContext.getTarget().getId() == RegistrationState.PHONE_VERIFICATION) {
            log.info("Send phone confirmation code");
        }

        if (stateContext.getExtendedState().getVariables().containsKey("test")) {
            log.info("Contains additional data");
        }

    }

}
