package com.fedorizvekov.caching.model.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.fedorizvekov.caching.model.entity.CachedData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CachedDataRowMapper implements RowMapper<CachedData> {

    public CachedData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return CachedData.builder()
                .id(resultSet.getLong("id"))
                .stringValue(resultSet.getString("string_value"))
                .localDateValue(resultSet.getDate("local_date_value").toLocalDate())
                .localTimeValue(resultSet.getTime("local_time_value").toLocalTime())
                .localDateTimeValue(resultSet.getTimestamp("local_date_time_value").toLocalDateTime())
                .integerValue(resultSet.getInt("integer_value"))
                .bigDecimalValue(resultSet.getBigDecimal("big_decimal_value"))
                .booleanValue(resultSet.getBoolean("boolean_value"))
                .build();
    }

}
