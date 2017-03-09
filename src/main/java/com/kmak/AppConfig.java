package com.kmak;

import com.kmak.DBUtils.DataSourceDBConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

/**
 * Created by Leaf.Ye on 2017/3/1.
 *
 * AppConfig类用于配置Spring容器(applicationContext.xml文件)的相关内容
 */
@Configuration
@ComponentScan(basePackages = {"com.kmak"},
        useDefaultFilters = false,
        excludeFilters = @ComponentScan.Filter(Controller.class))
@Import({DataSourceDBConfig.class})//@Import，引入指定的配置类，我们引入了Spring容器配置类和数据源事务配置类
public class AppConfig{
}
