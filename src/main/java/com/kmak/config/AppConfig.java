package com.kmak.config;

import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.regex.Pattern;

/**
 * Created by Leaf.Ye on 2017/3/1.
 * 父容器
 * AppConfig类用于配置Spring容器(applicationContext.xml文件)的相关内容
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.kmak.service","com.kmak.dao"})
@Import({DataSourceDBConfig.class})//@Import，引入指定的配置类，我们引入了Spring容器配置类和数据源事务配置类
public class AppConfig{

}
