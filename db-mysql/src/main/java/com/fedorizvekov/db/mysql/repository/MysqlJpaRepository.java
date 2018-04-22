package com.fedorizvekov.db.mysql.repository;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mysql.model.entity.TypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlJpaRepository extends JpaRepository<TypeValue, Long> {

    @Override
    long count();

    @Override
    Optional<TypeValue> findById(Long id);

    @Override
    List<TypeValue> findAll();

}
