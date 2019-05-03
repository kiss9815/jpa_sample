package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.demo.db2", 
    entityManagerFactoryRef = "data2EntityManager", 
    transactionManagerRef = "data2TransactionManager"
)
public class Db2Config {
    @Autowired
    private Environment env;
 
    @Bean(name="data2EntityManager")
    public LocalContainerEntityManagerFactoryBean data2EntityManager() {
        LocalContainerEntityManagerFactoryBean em  = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource2());
        em.setPackagesToScan( new String[] { "com.example.demo.vo.db2" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        em.setJpaVendorAdapter(vendorAdapter);
        
        System.out.println("log : " +  env.getProperty("jpa.hibernate.dialect"));
        Map<String, Object> jpaProperties = new HashMap<>();
        
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("jpa.hibernate.ddl-auto"));
        
        
        em.setJpaPropertyMap(jpaProperties);
 
        return em;
    }
 
    @Bean
    public HikariDataSource dataSource2() {
    	HikariConfig config = new HikariConfig();
    	config.setUsername(env.getProperty("db2.datasource.hikari.username")); 
    	config.setPassword(env.getProperty("db2.datasource.hikari.password")); 
    	config.setDriverClassName(env.getProperty("db2.datasource.hikari.driverClassName"));
    	config.setJdbcUrl( env.getProperty("db2.datasource.hikari.jdbc-url_1") );
    	config.setMaxLifetime( Long.parseLong(env.getProperty("db2.datasource.hikari.max-lifetime")) );
    	config.setConnectionTimeout(Long.parseLong( env.getProperty("db2.datasource.hikari.connection-timeout")));
    	config.setValidationTimeout(Long.parseLong( env.getProperty("db2.datasource.hikari.validation-timeout")));
    	
    	config.addDataSourceProperty( "cachePrepStmts" ,  env.getProperty("db2.datasource.hikari.data-source-properties.cachePrepStmts"));
        config.addDataSourceProperty( "prepStmtCacheSize" , env.getProperty("db2.datasource.hikari.data-source-properties.prepStmtCacheSize"));
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , env.getProperty("db2.datasource.hikari.data-source-properties.prepStmtCacheSqlLimit") );	
        config.addDataSourceProperty( "useServerPrepStmts" , env.getProperty("db2.datasource.hikari.data-source-properties.useServerPrepStmts") );
    	
    	config = new HikariDataSource( config );
    	HikariDataSource dataSource = new HikariDataSource( config );
        return dataSource;
    }
    
 
    @Bean(name="data2TransactionManager")
    public PlatformTransactionManager data2TransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(data2EntityManager().getObject());
        
        return transactionManager;
    }
}