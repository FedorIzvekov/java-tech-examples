package com.fedorizvekov.db.mssql.service;

import java.util.List;

public interface DatabaseApiService {

    long countDatabaseRows(String api);

    String getDatabaseRow(long id, String api);

    List<String> getDatabaseRows(String api);

}
