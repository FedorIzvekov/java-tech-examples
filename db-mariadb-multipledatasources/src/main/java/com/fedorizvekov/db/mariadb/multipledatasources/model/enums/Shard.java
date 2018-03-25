package com.fedorizvekov.db.mariadb.multipledatasources.model.enums;

import lombok.extern.log4j.Log4j2;

@Log4j2
public enum Shard {
    UNKNOWN,
    FIRST_SHARD,
    SECOND_SHARD;


    public static Shard fromName(String name) {
        try {

            return Shard.valueOf(name.toUpperCase());

        } catch (IllegalArgumentException exception) {
            log.error("No shard enum constant '{}'", name);
            return UNKNOWN;
        }
    }

}
