package com.fedorizvekov.caching.model.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.fedorizvekov.caching.model.dto.StatStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatStatementRowMapper implements RowMapper<StatStatement> {

    public StatStatement mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return StatStatement.builder()
                .query(resultSet.getString("query"))
                .calls(resultSet.getInt("calls"))
                .totalExecTime(resultSet.getFloat("total_exec_time"))
                .meanExecTime(resultSet.getFloat("mean_exec_time"))
                .build();
    }

}
