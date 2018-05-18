package com.fedorizvekov.db.clickhouse.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.clickhouse.model.entity.TypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickhouseJpaRepository extends JpaRepository<TypeValue, Long> {

    @Override
    long count();

    @Override
    Optional<TypeValue> findById(Long id);

    @Override
    List<TypeValue> findAll();

}
