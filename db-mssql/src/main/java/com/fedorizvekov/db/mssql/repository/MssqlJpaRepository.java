package com.fedorizvekov.db.mssql.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mssql.model.entity.TypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MssqlJpaRepository extends JpaRepository<TypeValue, Long> {

    @Override
    long count();

    @Override
    Optional<TypeValue> findById(Long id);

    @Override
    List<TypeValue> findAll();

}
