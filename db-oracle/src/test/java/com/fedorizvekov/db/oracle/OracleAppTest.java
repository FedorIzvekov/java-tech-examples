package com.fedorizvekov.db.oracle;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OracleAppTest {

    @Test
    public void shouldContextLoads() {
        OracleApp.main(new String[]{});
    }

}
