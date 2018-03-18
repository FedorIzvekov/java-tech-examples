package com.fedorizvekov.query.jpa.model.enums;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender {

    NOT_DEFINED((byte) 0),
    MALE((byte) 1),
    FEMALE((byte) 2);


    @Getter
    private final byte value;


    public static Gender fromValue(byte value) {
        return Stream.of(Gender.values())
                .filter(type -> type.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Gender value: '" + value + "', need: 0 = NOT_DEFINED, 1 = MALE, 2 = FEMALE"));
    }

}
