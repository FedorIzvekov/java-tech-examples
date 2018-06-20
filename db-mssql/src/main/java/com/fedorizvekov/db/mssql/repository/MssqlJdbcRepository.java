package com.fedorizvekov.db.mssql.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mssql.model.entity.TypeValue;

public interface MssqlJdbcRepository {

    long count();

    Optional<TypeValue> findById(Long id);

    List<TypeValue> findAll();

}
