package com.fedorizvekov.db.mariadb.multipledatasources.model.enums;

import java.util.Arrays;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.InvalidApiTypeException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public enum ApiType {
    FIRST_JPA,
    SECOND_JPA,
    FIRST_JDBC,
    SECOND_JDBC;


    public static ApiType fromName(String name) {
        try {

            return ApiType.valueOf(name.toUpperCase());

        } catch (IllegalArgumentException exception) {
            throw new InvalidApiTypeException("Unsupported Api Type '" + name + "', supported: " + Arrays.toString(ApiType.values()));
        }
    }

}
