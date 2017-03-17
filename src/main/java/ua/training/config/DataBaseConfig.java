package ua.training.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.training.dao.RedisDAO;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by dima on 23.02.17.
 */
@Configuration
@EnableTransactionManagement

@PropertySources({
        @PropertySource(value = "classpath:db.properties"),
        @PropertySource("classpath:messages_en.properties")
})

//@PropertySource("classpath:db.properties")
@EnableJpaRepositories("ua.training.repository")
public class DataBaseConfig {
    //    DB properties:
    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_DATABASE_PASSWORD = "db.password";

    //    Hibernate Configuration:
    private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "db.hibernate.show_sql";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "db.hibernate.hbm2ddl.auto";
    private static final String PROP_HIBERNATE_CONNECTION_CHARSET = "db.hibernate.connection.CharSet";
    private static final String PROP_HIBERNATE_CONNECTION_CHARACTERENCODING =
            "db.hibernate.connection.characterEncoding";
    private static final String PROP_HIBERNATE_CONNECTION_USEUNICODE = "db.hibernate.connection.useUnicode";

    //    EntityManager Configuration:
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "db.entitymanager.packages.to.scan";

    public static final String REDIS_CONNECTION_DATABASE = "db.jedisurl";

    @Resource
    private Environment environment;

    private Properties getHibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put("hibernate.show_sql", environment.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        properties.put("hibernate.connection.CharSet", environment.getRequiredProperty(PROP_HIBERNATE_CONNECTION_CHARSET));
        properties.put("hibernate.connection.characterEncoding",
                environment.getRequiredProperty(PROP_HIBERNATE_CONNECTION_CHARACTERENCODING));
        properties.put("hibernate.connection.useUnicode",
                environment.getRequiredProperty(PROP_HIBERNATE_CONNECTION_USEUNICODE));

        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(environment.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROP_DATABASE_PASSWORD));

        return dataSource;
    }

    /*@Bean
    public RedisDAO getRedisDao(){
        return new RedisDAO();
    }*/
}
