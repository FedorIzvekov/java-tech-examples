package com.fedorizvekov.db.mariadb.multipledatasources.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.second.SecondMariadbJpaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = {SecondMariadbJpaRepository.class},
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager"
)
@EnableTransactionManagement
public class DatasourceSecondConfig {

    @Value("${hibernate.dialect}")
    private String dialectProperties;

    @Value("${hibernate.hbm2ddl.auto}")
    private String ddlAutoProperties;


    @Bean("secondProperties")
    @ConfigurationProperties("datasource.second")
    public DataSourceProperties secondProperties() {
        return new DataSourceProperties();
    }


    @Bean("secondDataSource")
    public DataSource secondDataSource(@Qualifier("secondProperties") DataSourceProperties mariadbProperties) {
        return mariadbProperties.initializeDataSourceBuilder().build();
    }


    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean("secondEntityManager")
    public LocalContainerEntityManagerFactoryBean secondEntityManager(@Qualifier("secondDataSource") DataSource dataSource) {
        var vendorAdapter = new HibernateJpaVendorAdapter();
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.fedorizvekov.db.mariadb.multipledatasources.model.entity");
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(hibernateProperties());
        return entityManager;
    }


    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager(@Qualifier("secondEntityManager") EntityManagerFactory entityManager) {
        return new JpaTransactionManager(entityManager);
    }


    private Properties hibernateProperties() {
        return new Properties() {{
            put("hibernate.dialect", dialectProperties);
            put("hibernate.hbm2ddl.auto", ddlAutoProperties);
        }};
    }

}
