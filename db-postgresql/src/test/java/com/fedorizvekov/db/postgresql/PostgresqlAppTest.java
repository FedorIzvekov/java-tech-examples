package com.fedorizvekov.db.postgresql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostgresqlAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        PostgresqlApp.main(new String[]{});
    }

}
