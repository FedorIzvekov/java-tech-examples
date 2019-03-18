package com.fedorizvekov.query.data.rest.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ContactType {

    EMAIL((short) 0),
    PHONE((short) 1);


    private static final Map<Short, ContactType> types = Arrays.stream(ContactType.values())
            .collect(Collectors.toMap(ContactType::getValue, Function.identity()));


    @Getter
    private final short value;


    public static ContactType fromValue(short value) {
        return Optional.of(value)
                .map(types::get)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ContactType value: '" + value + "', need: 0 = EMAIL, 1 = PHONE"));
    }

}
