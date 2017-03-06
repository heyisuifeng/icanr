package com.kmak.DBUtils;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Created by Leaf.Ye on 2017/3/6.
 * 数据源配置类
 * 继承DBConfig类，实现父类的dataSource()方法
 */
public class DataSourceDBConfig extends DBConfig{

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/icanr_test?useUnicode\\=true;characterEncoding\\=utf8;autoReconnect\\=true");
        dataSource.setUsername("root");
        dataSource.setPassword("p@ssw0rd");
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        return dataSource;
    }

}
