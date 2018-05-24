package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.FIRST_JPA;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType.SECOND_JPA;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.model.enums.ApiType;
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


    public long countDatabaseRows(String databaseShard) {

        ApiType apiType = ApiType.fromName(databaseShard);
        long count = 0L;

        if (apiType == FIRST_JPA) {

            count = jpaRepository.count();

        } else if (apiType == SECOND_JPA) {

            count = secondJpaRepository.count();

        }

        return count;

    }


    public String getDatabaseRow(long id, String api) {

        ApiType apiType = ApiType.fromName(api);
        TypeValue typeValue = TypeValue.builder().build();

        if (apiType == FIRST_JPA) {

            typeValue = jpaRepository.findById(id).get();

        } else if (apiType == SECOND_JPA) {

            typeValue = secondJpaRepository.findById(id).get();

        }

        return typeValue.toString();

    }


    public List<String> getDatabaseRows(String databaseShard) {

        ApiType apiType = ApiType.fromName(databaseShard);
        List<TypeValue> list = emptyList();

        if (apiType == FIRST_JPA) {

            list = jpaRepository.findAll();

        } else if (apiType == SECOND_JPA) {

            list = secondJpaRepository.findAll();

        }

        return list.isEmpty()
                ? singletonList("QueryPerformanceJpaApp not support shard: '" + apiType + "'")
                : list.stream().map(Objects::toString).collect(Collectors.toList());

    }

}
