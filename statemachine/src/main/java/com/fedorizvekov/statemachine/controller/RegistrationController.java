package com.fedorizvekov.statemachine.controller;

import com.fedorizvekov.statemachine.model.enums.RegistrationEvent;
import com.fedorizvekov.statemachine.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/event/{userId}")
    public String sendEvent(@PathVariable Long userId, @RequestParam RegistrationEvent event) {
        return registrationService.sendEvent(userId, event);
    }


    @GetMapping("/state/{userId}")
    public String getCurrentState(@PathVariable Long userId) {
        return registrationService.getCurrentState(userId);
    }

}
