package com.fedorizvekov.caching.repository;

import java.util.Optional;
import com.fedorizvekov.caching.model.entity.CachedData;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRepository extends org.springframework.data.jpa.repository.JpaRepository<CachedData, Long> {

    @Override
    Optional<CachedData> findById(Long id);

}
