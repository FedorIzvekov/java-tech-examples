package com.fedorizvekov.db.oracle.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.oracle.model.entity.TypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OracleJpaRepository extends JpaRepository<TypeValue, Long> {

    @Override
    long count();

    @Override
    Optional<TypeValue> findById(Long id);

    @Override
    List<TypeValue> findAll();

}
