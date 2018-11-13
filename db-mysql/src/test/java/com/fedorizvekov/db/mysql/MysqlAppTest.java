package com.fedorizvekov.db.mysql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MysqlAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        MysqlApp.main(new String[]{});
    }

}
