package com.fedorizvekov.caching.repository.impl;

import java.util.List;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRepositoryImpl implements JdbcRepository {

    private static final String SQL_FIND_ALL_CACHED_DATA = "SELECT * FROM cached_data;";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<CachedData> dataRowMapper;


    public List<CachedData> findAll() {

        return jdbcTemplate.query(SQL_FIND_ALL_CACHED_DATA, dataRowMapper);

    }

}
