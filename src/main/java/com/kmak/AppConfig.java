package com.kmak;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by 1 on 2017/3/1.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.kmak")
public class AppConfig {
    @Bean
    public InternalResourceViewResolver resourceViewResolver() throws PropertyVetoException {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }

    @Bean
    public static DataSource dataSource() throws PropertyVetoException{
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/shiro_test?useUnicode\\=true;characterEncoding\\=utf8;autoReconnect\\=true");
        dataSource.setUsername("root");
        dataSource.setPassword("p@ssw0rd");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        return dataSource;
    }

   /* @Bean
    public LocalSessionFactoryBean sessionFactoryBean() throws PropertyVetoException{
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.format_sql","true");
        properties.setProperty("hibernate.use_sql_comments","false");
        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.setPackagesToScan("com.kmak.pojo");
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws PropertyVetoException{
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory((SessionFactory)sessionFactoryBean());
        return transactionManager;
    }*/
}
