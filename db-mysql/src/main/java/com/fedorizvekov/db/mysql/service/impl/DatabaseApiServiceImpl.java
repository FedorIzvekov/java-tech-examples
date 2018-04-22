package com.fedorizvekov.db.mysql.service.impl;

import static com.fedorizvekov.db.mysql.model.enums.ApiType.JPA;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;
import com.fedorizvekov.db.mysql.model.enums.ApiType;
import com.fedorizvekov.db.mysql.repository.MysqlJpaRepository;
import com.fedorizvekov.db.mysql.service.DatabaseApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseApiServiceImpl implements DatabaseApiService {

    private final MysqlJpaRepository jpaRepository;


    public String getDatabaseRow(long id, String api) {

        ApiType apiType = ApiType.fromName(api);
        TypeValue typeValue = TypeValue.builder().build();

        if (apiType == JPA) {
            typeValue = jpaRepository.findById(id).get();
        }

        return typeValue.toString();

    }


    public List<String> getDatabaseRows(String api) {

        ApiType apiType = ApiType.fromName(api);
        List<TypeValue> list = emptyList();

        if (apiType == JPA) {
            list = jpaRepository.findAll();
        }

        return list.stream().map(Objects::toString).collect(toList());

    }

}
