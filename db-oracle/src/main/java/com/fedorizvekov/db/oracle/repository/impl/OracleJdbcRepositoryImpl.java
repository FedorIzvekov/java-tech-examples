package com.fedorizvekov.db.oracle.repository.impl;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.oracle.model.entity.TypeValue;
import com.fedorizvekov.db.oracle.repository.OracleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OracleJdbcRepositoryImpl implements OracleJdbcRepository {

    private static final String SQL_COUNT = "SELECT COUNT(*) FROM type_value";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM type_value WHERE long_id = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM type_value";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<TypeValue> rowMapper;


    public long count() {

        return jdbcTemplate.queryForObject(SQL_COUNT, emptyMap(), Long.class);

    }


    public Optional<TypeValue> findById(Long id) {

        var typeValues = jdbcTemplate.query(SQL_FIND_BY_ID, singletonMap("id", id), rowMapper);

        return typeValues.isEmpty() ? empty() : of(typeValues.get(0));

    }


    public List<TypeValue> findAll() {

        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);

    }

}
