package com.fedorizvekov.db.mariadb.multipledatasources.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}
