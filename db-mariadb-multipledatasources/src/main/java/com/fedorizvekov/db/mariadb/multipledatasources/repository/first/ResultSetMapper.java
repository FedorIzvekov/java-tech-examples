package com.fedorizvekov.db.mariadb.multipledatasources.repository.first;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.fedorizvekov.db.mariadb.multipledatasources.model.entity.TypeValue;

public interface ResultSetMapper {

    TypeValue mapToTypeValue(ResultSet resultSet) throws SQLException;

}
