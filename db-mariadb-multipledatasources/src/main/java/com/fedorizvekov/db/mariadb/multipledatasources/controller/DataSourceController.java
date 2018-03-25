package com.fedorizvekov.db.mariadb.multipledatasources.controller;

import java.util.List;
import com.fedorizvekov.db.mariadb.multipledatasources.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataSourceController {

    private final DatabaseApiService databaseApiService;


    @GetMapping("/{database}/rows")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> getDatabaseRows(@PathVariable String database) {
        return databaseApiService.getDatabaseRows(database);
    }

}
