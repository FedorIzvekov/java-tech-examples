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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = {SecondMariadbJpaRepository.class},
        entityManagerFactoryRef = "secondMariadbEntityManager",
        transactionManagerRef = "secondMariadbTransactionManager"
)
@EnableTransactionManagement
public class SecondMariadbConfig {

    @Value("${mariadb.hibernate.dialect}")
    private String dialectProperties;

    @Value("${hibernate.hbm2ddl.auto}")
    private String ddlAutoProperties;


    @Bean("secondMariadbProperties")
    @ConfigurationProperties("second.mariadb.datasource")
    public DataSourceProperties mariadbProperties() {
        return new DataSourceProperties();
    }


    @Bean("secondMariadbDataSource")
    public DataSource mariadbDataSource(@Qualifier("secondMariadbProperties") DataSourceProperties mariadbProperties) {
        return mariadbProperties.initializeDataSourceBuilder().build();
    }


    @Bean("secondMariadbEntityManager")
    public LocalContainerEntityManagerFactoryBean mariadbEntityManager(@Qualifier("secondMariadbDataSource") DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.fedorizvekov.db.mariadb.multipledatasources.model.entity");
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(hibernateProperties());
        return entityManager;

    }


    @Bean(name = "secondMariadbTransactionManager")
    public PlatformTransactionManager mariadbTransactionManager(@Qualifier("secondMariadbEntityManager") EntityManagerFactory entityManager) {
        return new JpaTransactionManager(entityManager);
    }


    private Properties hibernateProperties() {
        return new Properties() {{
            put("hibernate.dialect", dialectProperties);
            put("hibernate.hbm2ddl.auto", ddlAutoProperties);
        }};
    }

}
