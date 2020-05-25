package com.fedorizvekov.caching.repository;

import java.util.List;
import com.fedorizvekov.caching.model.entity.CachedData;

public interface JdbcRepository {

    List<CachedData> findAll();

}
