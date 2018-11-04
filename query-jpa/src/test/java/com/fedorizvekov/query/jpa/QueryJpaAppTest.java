package com.fedorizvekov.query.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QueryJpaAppTest {

    @DisplayName("Should context loads")
    @Test
    void shouldContextLoads() {
        QueryJpaApp.main(new String[]{});
    }

}
