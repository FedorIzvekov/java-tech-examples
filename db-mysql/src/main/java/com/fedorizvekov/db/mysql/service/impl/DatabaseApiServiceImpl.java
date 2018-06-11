package com.fedorizvekov.db.mysql.service.impl;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.fedorizvekov.db.mysql.exception.NotFoundException;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;
import com.fedorizvekov.db.mysql.model.enums.ApiType;
import com.fedorizvekov.db.mysql.repository.MysqlCriteriaRepository;
import com.fedorizvekov.db.mysql.repository.MysqlJdbcRepository;
import com.fedorizvekov.db.mysql.repository.MysqlJpaRepository;
import com.fedorizvekov.db.mysql.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseApiServiceImpl implements DatabaseApiService {

    private final MysqlJpaRepository jpaRepository;
    private final MysqlJdbcRepository jdbcRepository;
    private final MysqlCriteriaRepository criteriaRepository;


    public long countDatabaseRows(String databaseShard) {

        ApiType apiType = ApiType.fromName(databaseShard);
        long count = 0L;

        switch (apiType) {
            case JPA:
                count = jpaRepository.count();
                break;
            case JDBC:
                count = jdbcRepository.count();
                break;
            case CRITERIA:
                count = criteriaRepository.count();
                break;
        }

        return count;
    }


    public String getDatabaseRow(long id, String api) {

        ApiType apiType = ApiType.fromName(api);
        Optional<TypeValue> typeValue = Optional.empty();

        switch (apiType) {
            case JPA:
                typeValue = jpaRepository.findById(id);
                break;
            case JDBC:
                typeValue = jdbcRepository.findById(id);
                break;
            case CRITERIA:
                typeValue = criteriaRepository.findById(id);
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
            case JPA:
                typeValues = jpaRepository.findAll();
                break;
            case JDBC:
                typeValues = jdbcRepository.findAll();
                break;
            case CRITERIA:
                typeValues = criteriaRepository.findAll();
                break;
        }

        return typeValues.stream().map(Objects::toString).collect(toList());

    }

}
