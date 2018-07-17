package com.fedorizvekov.db.postgresql.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.postgresql.model.entity.TypeValue;

public interface PostgresqlJdbcRepository {

    long count();

    Optional<TypeValue> findById(Long id);

    List<TypeValue> findAll();

}
