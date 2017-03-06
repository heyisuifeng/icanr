package com.kmak;

import com.kmak.javaConfig.WebContextConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Leaf.Ye on 2017/3/1.
 *
 * WebConfig类用于配置Web子容器的相关内容
 * 可以直接重写void addInterceptors(InterceptorRegistry registry)方法来注册自定义的监听器
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.kmak.*"})
public class WebConfig extends WebContextConfig{
}
