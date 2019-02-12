package com.fedorizvekov.db.mariadb.multipledatasources.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import com.fedorizvekov.db.mariadb.multipledatasources.repository.first.MariadbJpaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = {MariadbJpaRepository.class},
        entityManagerFactoryRef = "firstEntityManager",
        transactionManagerRef = "firstTransactionManager"
)
@EnableTransactionManagement
public class DatasourceFirstConfig {

    @Value("${hibernate.dialect}")
    private String dialectProperties;

    @Value("${hibernate.hbm2ddl.auto}")
    private String ddlAutoProperties;


    @Primary
    @Bean("firstProperties")
    @ConfigurationProperties("datasource.first")
    public DataSourceProperties firstProperties() {
        return new DataSourceProperties();
    }


    @Primary
    @Bean("firstDataSource")
    public DataSource firstDataSource(@Qualifier("firstProperties") DataSourceProperties mariadbProperties) {
        return mariadbProperties.initializeDataSourceBuilder().build();
    }


    @Primary
    @Bean("firstEntityManager")
    public LocalContainerEntityManagerFactoryBean firstEntityManager(@Qualifier("firstDataSource") DataSource dataSource) {
        var vendorAdapter = new HibernateJpaVendorAdapter();
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.fedorizvekov.db.mariadb.multipledatasources.model.entity");
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(hibernateProperties());
        return entityManager;
    }


    @Primary
    @Bean(name = "firstTransactionManager")
    public PlatformTransactionManager firstTransactionManager(@Qualifier("firstEntityManager") EntityManagerFactory entityManager) {
        return new JpaTransactionManager(entityManager);
    }


    private Properties hibernateProperties() {
        return new Properties() {{
            put("hibernate.dialect", dialectProperties);
            put("hibernate.hbm2ddl.auto", ddlAutoProperties);
        }};
    }

}
