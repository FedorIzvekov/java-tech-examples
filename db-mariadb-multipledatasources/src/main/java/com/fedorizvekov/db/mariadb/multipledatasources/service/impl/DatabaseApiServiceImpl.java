package com.fedorizvekov.db.mariadb.multipledatasources.service.impl;

import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.Shard.FIRST_SHARD;
import static com.fedorizvekov.db.mariadb.multipledatasources.model.enums.Shard.SECOND_SHARD;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.model.enums.Shard;
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

    public List<String> getDatabaseRows(String databaseShard) {

        Shard shard = Shard.fromName(databaseShard);
        List<TypeValue> list = emptyList();

        if (shard == FIRST_SHARD) {

            list = jpaRepository.findAll();

        } else if (shard == SECOND_SHARD) {

            list = secondJpaRepository.findAll();

        }

        return list.isEmpty()
                ? singletonList("QueryPerformanceJpaApp not support shard: '" + shard + "'")
                : list.stream().map(Objects::toString).collect(Collectors.toList());

    }

}
