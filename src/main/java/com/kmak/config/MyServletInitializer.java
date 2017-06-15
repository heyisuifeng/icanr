package com.kmak.config;

import com.kmak.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by Leaf.Ye on 2017/3/6.
 *
 * 用于代替web.xml的web容器配置类
 * 在这里配置过滤器、监听器、Servlet
 */
public class MyServletInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext container) throws ServletException {
        //配置spring提供的字符编码过滤器
        FilterRegistration.Dynamic filter = container.addFilter("encoding",new CharacterEncodingFilter());
        //配置过滤器的过滤路径
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");


        //以下的配置方式，在项目没有WebInitializer类的时候，配置，如果存在WebInitializer类，必须注解掉，否则项目启动不了，
        // WebInitializer继承了AbstractAnnotationConfigDispatcherServletInitializer，项目将从这里开始启动
        //基于注解配置的spring容器上下文
        //对应web.xml文件如下配置
        /**
         *<context-param>
         *<param-name>contextConfigLocation</param-name>
         *<param-value>classpath:application.xml</param-value>
         *</context-param>
         *
         *<listener>
         *<listener-class>org.springframework.config.context.ContextLoaderListener</listener-class>
         *</listener>
         */
        /*AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //注册spring容器配置类
        rootContext.register(AppConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));*/


        //基于注解配置的web容器上下文
        //对应web.xml文件如下配置
        /**
         *<servlet>
         *<servlet-name>spring</servlet-name>
         *<servlet-class>org.springframework.config.servlet.DispatcherServlet</servlet-class>
         *<init-param>
         *<param-name>contextConfigLocation</param-name>
         *<param-value>classpath:spring-servlet.xml</param-value>
         *</init-param>
         *<load-on-startup>1</load-on-startup>
         *</servlet>
         *<servlet-mapping>
         *<servlet-name>spring</servlet-name>
         *<url-pattern>/</url-pattern>
         *</servlet-mapping>
         */
        /*AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        //注册web容器配置类
        context.register(WebConfig.class);
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher",new DispatcherServlet(context));
        //配置映射路径
        servlet.addMapping("/");
        //启动顺序
        servlet.setLoadOnStartup(1);*/
    }
}
