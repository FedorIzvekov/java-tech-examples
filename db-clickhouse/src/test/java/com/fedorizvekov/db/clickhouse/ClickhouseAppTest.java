package com.fedorizvekov.db.clickhouse;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClickhouseAppTest {

    @Test
    public void shouldContextLoads() {
        ClickhouseApp.main(new String[]{});
    }

}