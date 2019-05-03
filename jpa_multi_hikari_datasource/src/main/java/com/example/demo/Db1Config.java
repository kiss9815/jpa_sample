package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
public class Db1Config  {
    @Autowired
    private Environment env;
     
    @Bean(name="data1EntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean data1EntityManager() { // LocalSessionFactory 와 동일 
    	//Spring에서 사용할 DataSource와 Entity가 위치한 Package들에 대한 검색을 모두 포함하게 됩니다.
    	
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource1());
        em.setPackagesToScan( new String[] { "com.example.demo.vo.db1" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        em.setJpaVendorAdapter(vendorAdapter);

        System.out.println("log : " +  env.getProperty("jpa.hibernate.dialect"));
        Map<String, Object> jpaProperties = new HashMap<>();
        
//        jpaProperties.put("hibernate.dialect", env.getProperty("jpa.hibernate.dialect"));
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("jpa.hibernate.ddl-auto"));
//        jpaProperties.put("hibernate.cache.region.factory_class", EhCacheRegionFactory.class.getName());
//        jpaProperties.put("hibernate.cache.use_second_level_cache", "true");
//        jpaProperties.put("hibernate.cache.use_query_cache", "true");
//        jpaProperties.put("hibernate.cache.use_minimal_puts", "true");
        
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",  env.getProperty("jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("jpa.hibernate.dialect"));
        
        em.setJpaPropertyMap(jpaProperties);
 
        return em;
    }
    
    @Primary
    @Bean
    public HikariDataSource dataSource1() {
    	
    	HikariConfig config = new HikariConfig();
    	config.setUsername(env.getProperty("db1.datasource.hikari.username")); 
    	config.setPassword(env.getProperty("db1.datasource.hikari.password")); 
    	config.setDriverClassName(env.getProperty("db1.datasource.hikari.driverClassName"));
    	config.setJdbcUrl( env.getProperty("db1.datasource.hikari.jdbc-url") );
    	config.setMaxLifetime( Long.parseLong(env.getProperty("db1.datasource.hikari.max-lifetime")) );
    	config.setConnectionTimeout(Long.parseLong( env.getProperty("db1.datasource.hikari.connection-timeout")));
    	config.setValidationTimeout(Long.parseLong( env.getProperty("db1.datasource.hikari.validation-timeout")));
    	
    	
    	config.addDataSourceProperty( "cachePrepStmts" ,  env.getProperty("db1.datasource.hikari.data-source-properties.cachePrepStmts"));
        config.addDataSourceProperty( "prepStmtCacheSize" , env.getProperty("db1.datasource.hikari.data-source-properties.prepStmtCacheSize"));
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , env.getProperty("db1.datasource.hikari.data-source-properties.prepStmtCacheSqlLimit") );	
        config.addDataSourceProperty( "useServerPrepStmts" , env.getProperty("db1.datasource.hikari.data-source-properties.useServerPrepStmts") );
        
        
    	HikariDataSource dataSource = new HikariDataSource( config );
    	////////////////////////////
    	/*String configFile = "src/main/resources/db1.properties";
    	HikariConfig cfg = new HikariConfig(configFile);
    	HikariDataSource dataSource = new HikariDataSource(cfg);*/
        /////////////////////////
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
