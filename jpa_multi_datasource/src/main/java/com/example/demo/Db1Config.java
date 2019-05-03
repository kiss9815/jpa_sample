package com.example.demo;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.demo.db1", 
    entityManagerFactoryRef = "data1EntityManager", 
    transactionManagerRef = "data1TransactionManager"
)
public class Db1Config extends HikariConfig {
    @Autowired
    private Environment env;
     
    @Bean(name="data1EntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean data1EntityManager() { // LocalSessionFactory 와 동일 
    	//Spring에서 사용할 DataSource와 Entity가 위치한 Package들에 대한 검색을 모두 포함하게 됩니다.
    	
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource1());
        em.setPackagesToScan( new String[] { "com.example.demo.db1" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(false);
        
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        System.out.println("log : "+  env.getProperty("jpa.hibernate.ddl-auto"));
        
        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Primary
    @Bean
    public DataSource dataSource1() {
    	System.out.println("dddddd" + env.getProperty("db1.datasource.url"));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        
        dataSource.setDriverClassName(env.getProperty("db1.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("db1.datasource.url"));
        dataSource.setUsername(env.getProperty("db1.datasource.username"));
        dataSource.setPassword(env.getProperty("db1.datasource.password"));
        
//        dataSource.setTestWhileIdle(testWhileIdle);     
//        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMills);
//        dataSource.setValidationQuery(validationQuery);
        
        return dataSource;
    }
 
    @Primary
    @Bean(name="data1TransactionManager")
    public PlatformTransactionManager data1TransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(data1EntityManager().getObject());
        return transactionManager;
    }
}
