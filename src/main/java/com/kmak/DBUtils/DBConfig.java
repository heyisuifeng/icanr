package com.kmak.DBUtils;

import com.kmak.DBUtils.JDBCAccess;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Leaf.Ye on 2017/3/3.
 * 基本的数据库配置
 * 统一配置了数据库访问工具和事务管理器
 * 具体的数据库配置类需要实现数据源创建的抽象方法
 * 这个类为实现不同的数据库源提供了接口
 */
public abstract class DBConfig {
    /**
     * 数据源，由子类实现(可扩展性)
     */
    public abstract DataSource dataSource();

    /**
     * 事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    /**
     * springJDBC Template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    /**
     * JDBC访问接口
     */
    @Bean
    public JDBCAccess jdbcAccess(){
        JDBCAccess jdbcAccess = new JDBCAccess();
        jdbcAccess.setJdbcTemplate(jdbcTemplate());
        return jdbcAccess;
    }
}
