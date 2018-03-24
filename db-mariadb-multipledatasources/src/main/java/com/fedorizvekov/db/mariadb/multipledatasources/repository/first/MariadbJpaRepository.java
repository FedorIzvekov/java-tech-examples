package com.fedorizvekov.db.mariadb.multipledatasources.repository.first;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MariadbJpaRepository extends JpaRepository<TypeValue, Long> {

    @Override
    long count();

    @Override
    Optional<TypeValue> findById(Long id);

    @Override
    List<TypeValue> findAll();

}
