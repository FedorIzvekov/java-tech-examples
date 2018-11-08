package com.fedorizvekov.db.clickhouse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClickhouseAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        ClickhouseApp.main(new String[]{});
    }

}
