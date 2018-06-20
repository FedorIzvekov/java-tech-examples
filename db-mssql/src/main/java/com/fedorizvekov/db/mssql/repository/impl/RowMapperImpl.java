package com.fedorizvekov.db.mssql.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import com.fedorizvekov.db.mssql.model.entity.TypeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RowMapperImpl implements RowMapper<TypeValue> {

    public TypeValue mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return TypeValue.builder()
                .longId(resultSet.getLong("long_id"))
                .databaseName(resultSet.getString("database_name"))
                .stringValue(resultSet.getString("string_value"))
                .charValue(resultSet.getString("char_value").charAt(0))
                .localDateValue(resultSet.getDate("local_date_value").toLocalDate())
                .localTimeValue(resultSet.getTime("local_time_value").toLocalTime())
                .localDateTimeValue(resultSet.getTimestamp("local_date_time_value").toLocalDateTime())
                .byteValue(resultSet.getByte("byte_value"))
                .shortValue(resultSet.getShort("short_value"))
                .integerValue(resultSet.getInt("integer_value"))
                .bigDecimalValue(resultSet.getBigDecimal("big_decimal_value"))
                .booleanValue(resultSet.getBoolean("boolean_value"))
                .uuidValue(UUID.fromString(resultSet.getString("uuid_value")))
                .build();
    }

}
