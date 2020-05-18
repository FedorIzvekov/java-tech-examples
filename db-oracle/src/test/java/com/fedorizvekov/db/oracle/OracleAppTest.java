package com.fedorizvekov.db.oracle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OracleAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        OracleApp.main(new String[]{});
    }

}
