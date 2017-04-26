package com.kmak;

import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.beans.PropertyVetoException;

/**
 * Created by Leaf.Ye on 2017/3/6.
 * 对应原本的xxx-servlet.xml文件
 * web容器上下文配置(也称web子容器)
 * 该类用于配置原本配置在xxx-servlet.xml中的信息
 * 继承org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter抽象类，
 * 通过重写其void addResourceHandlers(ResourceHandlerRegistry registry)方法实现静态资源过滤，
 * 重写void addInterceptors(InterceptorRegistry registry)方法来配置我们的监听器
 */
@Configuration
@EnableWebMvc
/*@ComponentScan(basePackages = {"com.kmak.*"},
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(Controller.class),
        excludeFilters = @ComponentScan.Filter(Service.class))*/
@ComponentScan(basePackages = {"com.kmak.*"},
        useDefaultFilters = false,//配置此项只扫描controller，而service和repository都不扫描
        includeFilters = @ComponentScan.Filter(Controller.class),
        excludeFilters = @ComponentScan.Filter(Service.class))
public class WebConfig extends WebMvcConfigurerAdapter{

    /**
     * JSP视图解析器
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    public InternalResourceViewResolver resourceViewResolver() throws PropertyVetoException {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }

    /**
     * 将对于静态资源的请求转发到Servlet容器的默认处理静态资源的servlet
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 静态资源过滤
     * @param registry
     */
   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets*//**")
     .addResourceLocations("/assets/")
     .setCachePeriod(31556926);
     }*/
}
