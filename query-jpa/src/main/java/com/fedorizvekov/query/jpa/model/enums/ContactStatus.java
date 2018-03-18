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

    CONFIRMED((byte) 0),
    NOT_CONFIRMED((byte) 1),
    REPLACED((byte) 2);


    private static final Map<Byte, ContactStatus> statuses = Arrays.stream(ContactStatus.values())
            .collect(Collectors.toMap(ContactStatus::getValue, Function.identity()));


    @Getter
    private final byte value;


    public static ContactStatus fromValue(byte value) {
        return Optional.of(value)
                .map(statuses::get)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CredentialStatus value: '" + value + "', need: 0 = CONFIRMED, 1 = NOT_CONFIRMED, 2 = REPLACED"));
    }

}
