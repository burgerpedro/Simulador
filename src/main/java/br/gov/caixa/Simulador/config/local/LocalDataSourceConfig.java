package br.gov.caixa.Simulador.config.local;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "br.gov.caixa.Simulador.repository.local",
        entityManagerFactoryRef = "localEntityManager",
        transactionManagerRef = "localTransactionManager"
)
public class LocalDataSourceConfig {

    // Spring will automatically create this bean and apply the properties
    @Primary
    @Bean(name = "localDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.local")
    public DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "localEntityManager")
    public LocalContainerEntityManagerFactoryBean localEntityManager(@Qualifier("localDataSource") DataSource localDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localDataSource);
        em.setPackagesToScan("br.gov.caixa.Simulador.model.local");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean(name = "localTransactionManager")
    public PlatformTransactionManager localTransactionManager(@Qualifier("localEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }
}
