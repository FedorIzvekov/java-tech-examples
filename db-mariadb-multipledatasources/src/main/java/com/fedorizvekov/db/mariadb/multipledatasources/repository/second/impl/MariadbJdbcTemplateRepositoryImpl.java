package com.fedorizvekov.db.mariadb.multipledatasources.repository.second.impl;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.MariadbJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor
public class MariadbJdbcTemplateRepositoryImpl implements MariadbJdbcTemplateRepository {

    private static final String SQL_COUNT = "SELECT COUNT(*) FROM type_value";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM type_value WHERE long_id = :id";
    private static final String SQL_FIND_ALL = "SELECT * FROM type_value";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<TypeValue> rowMapper;


    public long count() {

        try {

            return jdbcTemplate.queryForObject(SQL_COUNT, emptyMap(), Long.class);

        } catch (DataAccessException exception) {
            log.error("Something went wrong, because: ", exception);
        }

        return 0;
    }


    public Optional<TypeValue> findById(Long id) {

        try {

            TypeValue typeValue = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, singletonMap("id", id), rowMapper);
            return Optional.of(typeValue);

        } catch (DataAccessException exception) {
            log.error("Something went wrong, because: ", exception);
        }

        return Optional.empty();
    }


    public List<TypeValue> findAll() {

        List<TypeValue> typeValues = new ArrayList<>();

        try {

            return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);

        } catch (DataAccessException exception) {
            log.error("Something went wrong, because: ", exception);
        }

        return typeValues;
    }

}
