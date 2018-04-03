package com.fedorizvekov.db.postgresql.controller;

import java.util.List;
import com.fedorizvekov.db.postgresql.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PostgresqlController {

    private final DatabaseApiService databaseApiService;


    @GetMapping("/{api}/row/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDatabaseRow(@PathVariable String api, @PathVariable long id) {
        return databaseApiService.getDatabaseRow(id, api);
    }


    @GetMapping("/{api}/rows")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> getDatabaseRows(@PathVariable String api) {
        return databaseApiService.getDatabaseRows(api);
    }

}
