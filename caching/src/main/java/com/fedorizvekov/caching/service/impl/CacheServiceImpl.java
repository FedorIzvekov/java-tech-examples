package com.fedorizvekov.caching.service.impl;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.repository.JpaRepository;
import com.fedorizvekov.caching.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final JpaRepository jpaRepository;
    private final JdbcRepository jdbcRepository;


    public Optional<CachedData> simpleFindById(long id) {
        return jpaRepository.findById(id);
    }


    public List<CachedData> simpleFindAll() {
        return jdbcRepository.findAll();
    }

}
