package com.fedorizvekov.caching.repository;

import java.util.List;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.model.dto.StatStatement;

public interface JdbcRepository {

    List<CachedData> findAll();

    List<StatStatement> findStatStatementByTableName(String tableName);

}
