package com.fedorizvekov.db.mssql.controller;

import java.util.List;
import com.fedorizvekov.db.mssql.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{api}")
@RequiredArgsConstructor
public class MssqlController {

    private final DatabaseApiService databaseApiService;


    @GetMapping("/rows/count")
    @ResponseStatus(HttpStatus.OK)
    public long countDatabaseRows(@PathVariable String api) {
        return databaseApiService.countDatabaseRows(api);
    }


    @GetMapping("/row/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getDatabaseRow(@PathVariable String api, @PathVariable long id) {
        return databaseApiService.getDatabaseRow(id, api);
    }


    @GetMapping("/rows")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getDatabaseRows(@PathVariable String api) {
        return databaseApiService.getDatabaseRows(api);
    }

}
