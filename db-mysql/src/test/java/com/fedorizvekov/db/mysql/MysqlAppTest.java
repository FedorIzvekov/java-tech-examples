package com.fedorizvekov.db.mysql;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MysqlAppTest {

    @Test
    public void shouldContextLoads() {
        MysqlApp.main(new String[]{});
    }

}
