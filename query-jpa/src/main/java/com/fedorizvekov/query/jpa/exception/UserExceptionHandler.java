package com.fedorizvekov.query.jpa.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.persistence.PersistenceException;
import com.fedorizvekov.query.jpa.model.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class UserExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException exception) {
        return ErrorResponse.builder()
                .errorCode(NOT_FOUND.value())
                .errorMessage(logAndCreateErrorMessage("NotFoundException", exception.getMessage()))
                .build();
    }


    @ExceptionHandler({PersistenceException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerPersistenceException(Exception exception) {
        return ErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR.value())
                .errorMessage(logAndCreateErrorMessage("PersistenceException", exception.getMessage()))
                .build();
    }


    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerOtherException(Exception exception) {
        return ErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR.value())
                .errorMessage(logAndCreateErrorMessage("Exception: Something went wrong, because", exception.getMessage()))
                .build();
    }


    private String logAndCreateErrorMessage(String exceptionType, String message) {
        var errorMessage = "RESPONSE ERROR, because: " + exceptionType + ": " + message;
        log.error(errorMessage);
        return errorMessage;
    }

}
