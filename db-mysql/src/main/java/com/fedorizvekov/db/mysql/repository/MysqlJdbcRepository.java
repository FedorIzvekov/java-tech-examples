package com.fedorizvekov.db.mysql.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;

public interface MysqlJdbcRepository {

    long count();

    Optional<TypeValue> findById(Long id);

    List<TypeValue> findAll();

}
