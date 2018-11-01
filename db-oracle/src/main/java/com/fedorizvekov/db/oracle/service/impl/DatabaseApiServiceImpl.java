package com.fedorizvekov.db.oracle.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.fedorizvekov.db.oracle.exception.NotFoundException;
import com.fedorizvekov.db.oracle.model.entity.TypeValue;
import com.fedorizvekov.db.oracle.model.enums.ApiType;
import com.fedorizvekov.db.oracle.repository.OracleJdbcRepository;
import com.fedorizvekov.db.oracle.repository.OracleJpaRepository;
import com.fedorizvekov.db.oracle.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseApiServiceImpl implements DatabaseApiService {

    private final OracleJpaRepository jpaRepository;
    private final OracleJdbcRepository jdbcRepository;


    public long countDatabaseRows(String databaseShard) {
        var apiType = ApiType.fromName(databaseShard);
        var count = 0L;

        switch (apiType) {
            case JPA:
                count = jpaRepository.count();
                break;
            case JDBC:
                count = jdbcRepository.count();
                break;
        }

        return count;
    }


    public String getDatabaseRow(long id, String api) {
        var apiType = ApiType.fromName(api);
        Optional<TypeValue> typeValue = Optional.empty();

        switch (apiType) {
            case JPA:
                typeValue = jpaRepository.findById(id);
                break;
            case JDBC:
                typeValue = jdbcRepository.findById(id);
                break;
        }

        return typeValue
                .orElseThrow(() -> new NotFoundException("Not found TypeValue with id '" + id + "'"))
                .toString();
    }


    public List<String> getDatabaseRows(String api) {
        var apiType = ApiType.fromName(api);
        List<TypeValue> typeValues = emptyList();

        switch (apiType) {
            case JPA:
                typeValues = jpaRepository.findAll();
                break;
            case JDBC:
                typeValues = jdbcRepository.findAll();
                break;
        }

        return typeValues.stream().map(Objects::toString).collect(toList());
    }

}
