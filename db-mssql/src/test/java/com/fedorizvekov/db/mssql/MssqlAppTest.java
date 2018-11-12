package com.fedorizvekov.db.mssql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MssqlAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        MssqlApp.main(new String[]{});
    }

}
