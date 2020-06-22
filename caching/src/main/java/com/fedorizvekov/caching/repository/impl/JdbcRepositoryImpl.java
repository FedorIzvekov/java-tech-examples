package com.fedorizvekov.caching.repository.impl;

import java.util.List;
import com.fedorizvekov.caching.model.dto.StatStatement;
import com.fedorizvekov.caching.model.entity.CachedData;
import com.fedorizvekov.caching.repository.JdbcRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRepositoryImpl implements JdbcRepository {

    private static final String SQL_FIND_ALL_CACHED_DATA = "SELECT * FROM cached_data;";
    private static final String SQL_PG_STAT_STATEMENTS =
            "SELECT query, calls, total_exec_time, mean_exec_time "
                    + "FROM pg_stat_statements "
                    + "WHERE query ILIKE '%FROM%{tableName}%' "
                    + "ORDER BY pg_stat_statements.stats_since DESC;";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<CachedData> dataRowMapper;
    private final RowMapper<StatStatement> statStatementRowMapper;


    @Timed(value = "get.all.data.duration", extraTags = {"source", "POSTGRESQL"})
    public List<CachedData> findAll() {

        return jdbcTemplate.query(SQL_FIND_ALL_CACHED_DATA, dataRowMapper);

    }


    public List<StatStatement> findStatStatementByTableName(String tableName) {

        var sql = SQL_PG_STAT_STATEMENTS.replace("{tableName}", tableName);
        return jdbcTemplate.query(sql, statStatementRowMapper);

    }

}
