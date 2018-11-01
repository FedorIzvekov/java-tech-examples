package com.fedorizvekov.db.mariadb.multipledatasources.repository.first.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import com.fedorizvekov.db.mariadb.multipledatasources.exception.DataBaseException;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJdbcRepository;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.ResultSetMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class MariadbJdbcRepositoryImpl implements MariadbJdbcRepository {

    private static final String SQL_COUNT = "SELECT COUNT(*) FROM type_value";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM type_value WHERE long_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM type_value";

    private final DataSource dataSource;
    private final ResultSetMapper resultSetMapper;


    public MariadbJdbcRepositoryImpl(
            @Qualifier("mariadbDataSource") DataSource dataSource,
            ResultSetMapper resultSetMapper
    ) {
        this.dataSource = dataSource;
        this.resultSetMapper = resultSetMapper;
    }


    public long count() {

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(SQL_COUNT);
             var resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException exception) {
            log.error("SQLException, because: ", exception);
            throw new DataBaseException(exception.getMessage());
        }

        return 0;
    }


    public Optional<TypeValue> findById(Long id) {

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(resultSetMapper.mapToTypeValue(resultSet));
                }

            }

        } catch (SQLException exception) {
            log.error("SQLException, because: ", exception);
            throw new DataBaseException(exception.getMessage());
        }

        return Optional.empty();
    }


    public List<TypeValue> findAll() {

        List<TypeValue> typeValues = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                typeValues.add(resultSetMapper.mapToTypeValue(resultSet));
            }

        } catch (SQLException exception) {
            log.error("SQLException, because: ", exception);
            throw new DataBaseException(exception.getMessage());
        }

        return typeValues;
    }

}
