package com.fedorizvekov.db.mariadb.multipledatasources;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MariadbMultipleDataSourcesAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        MariadbMultipleDataSourcesApp.main(new String[]{});
    }

}
