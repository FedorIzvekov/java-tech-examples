package com.fedorizvekov.db.oracle.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.oracle.model.entity.TypeValue;

public interface OracleJdbcRepository {

    long count();

    Optional<TypeValue> findById(Long id);

    List<TypeValue> findAll();

}
