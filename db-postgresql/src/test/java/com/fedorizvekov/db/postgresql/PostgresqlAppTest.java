package com.fedorizvekov.db.postgresql;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostgresqlAppTest {

    @Test
    public void shouldContextLoads() {
        PostgresqlApp.main(new String[]{});
    }

}
