package com.fedorizvekov.db.mariadb.multipledatasources;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MariadbMultipleDataSourcesAppTest {

    @Test
    public void shouldContextLoads() {
        MariadbMultipleDataSourcesApp.main(new String[]{});
    }

}
