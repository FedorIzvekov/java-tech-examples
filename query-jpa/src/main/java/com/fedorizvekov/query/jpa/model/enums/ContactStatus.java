package com.fedorizvekov.query.jpa.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ContactStatus {

    CONFIRMED((short) 0),
    NOT_CONFIRMED((short) 1),
    REPLACED((short) 2);


    private static final Map<Short, ContactStatus> statuses = Arrays.stream(ContactStatus.values())
            .collect(Collectors.toMap(ContactStatus::getValue, Function.identity()));


    @Getter
    private final short value;


    public static ContactStatus fromValue(short value) {
        return Optional.of(value)
                .map(statuses::get)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CredentialStatus value: '" + value + "', need: 0 = CONFIRMED, 1 = NOT_CONFIRMED, 2 = REPLACED"));
    }

}
