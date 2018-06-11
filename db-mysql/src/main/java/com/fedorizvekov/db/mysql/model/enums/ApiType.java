package com.fedorizvekov.db.mysql.model.enums;

import java.util.Arrays;
import com.fedorizvekov.db.mysql.exception.InvalidApiTypeException;

public enum ApiType {

    JPA,
    JDBC,
    CRITERIA;


    public static ApiType fromName(String name) {
        try {

            return ApiType.valueOf(name.toUpperCase());

        } catch (IllegalArgumentException exception) {
            throw new InvalidApiTypeException("Unsupported Api Type '" + name + "', supported: " + Arrays.toString(ApiType.values()));
        }
    }

}
