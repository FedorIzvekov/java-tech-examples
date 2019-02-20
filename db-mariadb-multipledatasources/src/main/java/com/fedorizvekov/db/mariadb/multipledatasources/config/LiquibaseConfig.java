package com.fedorizvekov.db.mariadb.multipledatasources.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }


    @Bean("firstLiquibaseProperties")
    @ConfigurationProperties(prefix = "datasource.fist.liquibase")
    public LiquibaseProperties firstLiquibaseProperties() {
        return new LiquibaseProperties();
    }


    @Bean("secondLiquibaseProperties")
    @ConfigurationProperties(prefix = "datasource.second.liquibase")
    public LiquibaseProperties secondLiquibaseProperties() {
        return new LiquibaseProperties();
    }


    @Bean("firstSpringLiquibase")
    public SpringLiquibase firstSpringLiquibase(
            @Qualifier("firstDataSource") DataSource dataSource,
            @Qualifier("firstLiquibaseProperties") LiquibaseProperties properties
    ) {
        return springLiquibase(dataSource, properties);
    }



    @Bean("secondSpringLiquibase")
    public SpringLiquibase secondSpringLiquibase(
            @Qualifier("secondDataSource") DataSource dataSource,
            @Qualifier("secondLiquibaseProperties") LiquibaseProperties properties
    ) {
        return springLiquibase(dataSource, properties);
    }

}
