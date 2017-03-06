package com.kmak.javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
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
public class WebContextConfig extends WebMvcConfigurerAdapter{

    /**
     * 静态资源过滤
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**")
                .addResourceLocations("/statics/")
                .setCachePeriod(31556926);
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
}
