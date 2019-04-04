package com.fedorizvekov.statemachine.service.impl;

import java.util.HashMap;
import java.util.Map;
import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.model.enums.RegistrationState;
import com.fedorizvekov.statemachine.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final StateMachineFactory<RegistrationState, RegistrationEvent> factory;
    private final Map<Long, StateMachine<RegistrationState, RegistrationEvent>> userMachines = new HashMap<>();


    private StateMachine<RegistrationState, RegistrationEvent> getOrCreateStateMachine(Long userId) {
        return userMachines.computeIfAbsent(userId, id -> factory.getStateMachine(String.valueOf(userId)));
    }


    public String sendEvent(Long userId, RegistrationEvent registrationEvent) {
        var stateMachine = getOrCreateStateMachine(userId);
        stateMachine.sendEvent(registrationEvent);
        return getCurrentState(userId);
    }


    public String getCurrentState(Long userId) {
        var stateMachine = getOrCreateStateMachine(userId);
        return "User with id = '" + userId + "' has current state: " + stateMachine.getState().getId();
    }

}
