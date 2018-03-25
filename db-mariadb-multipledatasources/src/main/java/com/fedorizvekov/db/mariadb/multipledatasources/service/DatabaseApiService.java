package com.fedorizvekov.db.mariadb.multipledatasources.service;

import java.util.List;

public interface DatabaseApiService {

    List<String> getDatabaseRows(String databaseShard);

}
