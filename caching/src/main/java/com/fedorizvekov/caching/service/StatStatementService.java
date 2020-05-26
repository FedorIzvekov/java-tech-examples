package com.fedorizvekov.caching.service;

import java.util.List;
import com.fedorizvekov.caching.model.dto.StatStatement;

public interface StatStatementService {

    List<StatStatement> getStatStatementByTableName(String tableName);

}
