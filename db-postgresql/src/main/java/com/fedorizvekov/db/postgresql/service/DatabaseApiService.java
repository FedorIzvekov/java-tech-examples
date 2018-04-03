package com.fedorizvekov.db.postgresql.service;

import java.util.List;

public interface DatabaseApiService {

    String getDatabaseRow(long id, String api);

    List<String> getDatabaseRows(String api);

}
