package com.fedorizvekov.db.mariadb.multipledatasources.repository.first.impl;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.ResultSetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultSetMapperImpl implements ResultSetMapper {

    public TypeValue mapToTypeValue(ResultSet resultSet) throws SQLException {
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
                .uuidValue(binaryToUUID(resultSet.getBytes("uuid_value")))
                .build();
    }


    private UUID binaryToUUID(byte[] binary) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(binary);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }

}
