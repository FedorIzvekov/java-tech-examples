package com.fedorizvekov.query.jpa;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueryJpaAppTest {

    @Test
    public void shouldContextLoads() {
        QueryJpaApp.main(new String[]{});
    }

}
