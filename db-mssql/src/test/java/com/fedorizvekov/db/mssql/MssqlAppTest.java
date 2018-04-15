package com.fedorizvekov.db.mssql;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MssqlAppTest {

    @Test
    public void shouldContextLoads() {
        MssqlApp.main(new String[]{});
    }

}
