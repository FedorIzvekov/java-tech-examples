package com.fedorizvekov.statemachine.service;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;

public interface RegistrationService {

    String getCurrentState(Long userId);

    String sendEvent(Long userId, RegistrationEvent registrationEvent);

}
