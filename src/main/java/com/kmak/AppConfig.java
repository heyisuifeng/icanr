package com.kmak;

import com.kmak.javaConfig.SpringContextConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Leaf.Ye on 2017/3/1.
 *
 * AppConfig类用于配置Spring容器(applicationContext文件)的相关内容
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.kmak.*"})
@Import({SpringContextConfig.class,BasicDataSource.class})//@Import，引入指定的配置类，我们引入了Spring容器配置类和数据源事务配置类
public class AppConfig{
}
