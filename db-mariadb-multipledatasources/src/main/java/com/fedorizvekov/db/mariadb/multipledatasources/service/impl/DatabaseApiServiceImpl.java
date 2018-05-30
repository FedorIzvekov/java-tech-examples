package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.NotFoundException;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJpaRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.MariadbJdbcTemplateRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.SecondMariadbJpaRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseApiServiceImpl implements DatabaseApiService {

    private final MariadbJpaRepository jpaRepository;
    private final SecondMariadbJpaRepository secondJpaRepository;
    private final MariadbJdbcRepository jdbcRepository;
    private final MariadbJdbcTemplateRepository jdbcTemplateRepository;


    public long countDatabaseRows(String api) {

        ApiType apiType = ApiType.fromName(api);
        long count = 0L;

        switch (apiType) {
            case FIRST_JPA:
                count = jpaRepository.count();
                break;
            case SECOND_JPA:
                count = secondJpaRepository.count();
                break;
            case FIRST_JDBC:
                count = jdbcRepository.count();
                break;
            case SECOND_JDBC:
                count = jdbcTemplateRepository.count();
                break;
        }

        return count;
    }


    public String getDatabaseRow(long id, String api) {

        ApiType apiType = ApiType.fromName(api);
        Optional<TypeValue> typeValue = Optional.empty();

        switch (apiType) {
            case FIRST_JPA:
                typeValue = jpaRepository.findById(id);
                break;
            case SECOND_JPA:
                typeValue = secondJpaRepository.findById(id);
                break;
            case FIRST_JDBC:
                typeValue = jdbcRepository.findById(id);
                break;
            case SECOND_JDBC:
                typeValue = jdbcTemplateRepository.findById(id);
                break;
        }

        return typeValue
                .orElseThrow(() -> new NotFoundException("Not found TypeValue with id '" + id + "'"))
                .toString();
    }


    public List<String> getDatabaseRows(String api) {

        ApiType apiType = ApiType.fromName(api);
        List<TypeValue> typeValues = emptyList();

        switch (apiType) {
            case FIRST_JPA:
                typeValues = jpaRepository.findAll();
                break;
            case SECOND_JPA:
                typeValues = secondJpaRepository.findAll();
                break;
            case FIRST_JDBC:
                typeValues = jdbcRepository.findAll();
                break;
            case SECOND_JDBC:
                typeValues = jdbcTemplateRepository.findAll();
                break;
        }

        return typeValues.stream().map(Objects::toString).collect(Collectors.toList());
    }

}
