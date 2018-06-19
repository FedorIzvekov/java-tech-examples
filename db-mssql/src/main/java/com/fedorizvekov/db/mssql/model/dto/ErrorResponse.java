package com.fedorizvekov.db.mssql.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private final int errorCode;
    private final String errorMessage;

}