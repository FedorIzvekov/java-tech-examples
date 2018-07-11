package com.fedorizvekov.db.clickhouse.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.clickhouse.model.entity.TypeValue;

public interface ClickhouseJdbcRepository {

    long count();

    Optional<TypeValue> findById(Long id);

    List<TypeValue> findAll();

}
