package com.fedorizvekov.db.mariadb.multipledatasources.repository.first;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;

public interface MariadbJdbcRepository {

    long count();

    Optional<TypeValue> findById(Long id);

    List<TypeValue> findAll();

}
