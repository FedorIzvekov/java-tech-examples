package com.fedorizvekov.caching.service.impl;

import java.util.List;
import com.fedorizvekov.caching.model.dto.StatStatement;
import com.fedorizvekov.caching.repository.JdbcRepository;
import com.fedorizvekov.caching.service.StatStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatStatementServiceImpl implements StatStatementService {

    private final JdbcRepository jdbcRepository;


    public List<StatStatement> getStatStatementByTableName(String tableName) {

        return jdbcRepository.findStatStatementByTableName(tableName);

    }

}
