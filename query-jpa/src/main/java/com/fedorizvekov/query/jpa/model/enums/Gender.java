package com.fedorizvekov.query.jpa.model.enums;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender {

    NOT_DEFINED((short) 0),
    MALE((short) 1),
    FEMALE((short) 2);


    @Getter
    private final short value;


    public static Gender fromValue(short value) {
        return Stream.of(Gender.values())
                .filter(type -> type.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Gender value: '" + value + "', need: 0 = NOT_DEFINED, 1 = MALE, 2 = FEMALE"));
    }

}
