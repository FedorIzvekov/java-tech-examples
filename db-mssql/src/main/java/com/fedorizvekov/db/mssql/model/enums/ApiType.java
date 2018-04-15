package com.fedorizvekov.db.mssql.model.enums;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum ApiType {

    JPA,
    UNKNOWN;


    public static ApiType fromName(String name) {
        try {

            return ApiType.valueOf(name.toUpperCase());

        } catch (IllegalArgumentException exception) {
            log.error("No ApiType enum constant '{}'", name);
            return UNKNOWN;
        }
    }

}
