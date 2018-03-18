package com.fedorizvekov.query.jpa.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ContactType {

    EMAIL((byte) 0),
    PHONE((byte) 1);


    private static final Map<Byte, ContactType> types = Arrays.stream(ContactType.values())
            .collect(Collectors.toMap(ContactType::getValue, Function.identity()));


    @Getter
    private final byte value;


    public static ContactType fromValue(byte value) {
        return Optional.of(value)
                .map(types::get)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ContactType value: '" + value + "', need: 0 = EMAIL, 1 = PHONE"));
    }

}
