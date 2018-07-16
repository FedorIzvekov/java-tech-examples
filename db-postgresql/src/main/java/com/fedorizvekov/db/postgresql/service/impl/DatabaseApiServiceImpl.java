package com.fedorizvekov.db.postgresql.service.impl;

import static com.fedorizvekov.db.postgresql.model.enums.ApiType.JPA;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.fedorizvekov.db.postgresql.exception.NotFoundException;
import com.fedorizvekov.db.postgresql.model.entity.TypeValue;
import com.fedorizvekov.db.postgresql.model.enums.ApiType;
import com.fedorizvekov.db.postgresql.repository.PostgresqlJpaRepository;
import com.fedorizvekov.db.postgresql.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseApiServiceImpl implements DatabaseApiService {

    private final PostgresqlJpaRepository jpaRepository;


    public long countDatabaseRows(String databaseShard) {

        ApiType apiType = ApiType.fromName(databaseShard);
        long count = 0L;

        if (apiType == JPA) {

            count = jpaRepository.count();

        }

        return count;

    }


    public String getDatabaseRow(long id, String api) {

        ApiType apiType = ApiType.fromName(api);
        Optional<TypeValue> typeValue = Optional.empty();

        if (apiType == JPA) {

            typeValue = jpaRepository.findById(id);

        }

        return typeValue
                .orElseThrow(() -> new NotFoundException("Not found TypeValue with id '" + id + "'"))
                .toString();

    }


    public List<String> getDatabaseRows(String api) {

        ApiType apiType = ApiType.fromName(api);
        List<TypeValue> typeValues = emptyList();

        if (apiType == JPA) {

            typeValues = jpaRepository.findAll();

        }

        return typeValues.stream().map(Objects::toString).collect(toList());

    }

}
