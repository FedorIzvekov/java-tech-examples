package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJpaRepository;
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


    public long countDatabaseRows(String databaseShard) {

        ApiType apiType = ApiType.fromName(databaseShard);

        switch (apiType) {
            case FIRST_JPA:
                return jpaRepository.count();
            case SECOND_JPA:
                return secondJpaRepository.count();
            case FIRST_JDBC:
                return jdbcRepository.count();
            default:
                return 0L;
        }

    }


    public String getDatabaseRow(long id, String api) {

        ApiType apiType = ApiType.fromName(api);

        switch (apiType) {
            case FIRST_JPA:
                return jpaRepository.findById(id).get().toString();
            case SECOND_JPA:
                return secondJpaRepository.findById(id).get().toString();
            case FIRST_JDBC:
                return jdbcRepository.findById(id).get().toString();
            default:
                return TypeValue.builder().build().toString();

        }
    }


    public List<String> getDatabaseRows(String databaseShard) {

        ApiType apiType = ApiType.fromName(databaseShard);
        List<TypeValue> list = emptyList();

        switch (apiType) {
            case FIRST_JPA:
                list = jpaRepository.findAll();
                break;
            case SECOND_JPA:
                list = secondJpaRepository.findAll();
                break;
            case FIRST_JDBC:
                list = jdbcRepository.findAll();
                break;
        }

        return list.isEmpty()
                ? singletonList("QueryPerformanceJpaApp not support shard: '" + apiType + "'")
                : list.stream().map(Objects::toString).collect(Collectors.toList());

    }

}
