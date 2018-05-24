package com.fedorizvekov.db.mariadb.multipledatasources.model.enums;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum ApiType {
    UNKNOWN,
    FIRST_JPA,
    SECOND_JPA;


    public static ApiType fromName(String name) {
        try {

            return ApiType.valueOf(name.toUpperCase());

        } catch (IllegalArgumentException exception) {
            log.error("No shard enum constant '{}'", name);
            return UNKNOWN;
        }
    }

}
