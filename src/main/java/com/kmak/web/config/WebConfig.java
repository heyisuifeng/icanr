package com.kmak.web.config;

import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leaf.Ye on 2017/3/6.
 * 对应原本的xxx-servlet.xml文件
 * web容器上下文配置(也称web子容器)
 * 该类用于配置原本配置在xxx-servlet.xml中的信息
 * 继承org.springframework.config.servlet.config.annotation.WebMvcConfigurerAdapter抽象类，
 * 通过重写其void addResourceHandlers(ResourceHandlerRegistry registry)方法实现静态资源过滤，
 * 重写void addInterceptors(InterceptorRegistry registry)方法来配置我们的监听器
 */
@Configuration
@EnableWebMvc
/*@ComponentScan(basePackages = {"com.kmak.*"},
        useDefaultFilters = false,//配置此项只扫描controller，而service和repository都不扫描
        includeFilters = @ComponentScan.Filter(Controller.class),
        excludeFilters = @ComponentScan.Filter(Service.class))*/
@ComponentScan(basePackages = {"com.kmak.web.controller"})
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
     * 文件上传下载解析器
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        //500M，这里单位是b 524288000b = (500M*1024)*1024
        commonsMultipartResolver.setMaxUploadSize(524288000);
        //40k
        commonsMultipartResolver.setMaxInMemorySize(40960);
        return commonsMultipartResolver;
    }

    /**
     * 消息转换器，spring默认注册了以下转换器，但是为了让json转换器使用自定义的日期格式，需要重新配置
     * @return
     */
    private List<HttpMessageConverter<?>> createMessageConverters(){
        List<HttpMessageConverter<?>> converters = new ArrayList<>();

        //文本消息转换器
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        //文本消息转换器所支持的文本类型
        ArrayList<MediaType> textTypes = new ArrayList<>();
        //原生格式
        textTypes.add(MediaType.TEXT_PLAIN);
        //HTML格式
        textTypes.add(MediaType.TEXT_HTML);
        //以us-ascii编码XML的内容
        textTypes.add(MediaType.TEXT_XML);
        //以指定字符集编码的xml内容
        textTypes.add(MediaType.APPLICATION_XML);
        stringConverter.setSupportedMediaTypes(textTypes);
        converters.add(stringConverter);

        //JSON 消息转换器
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ArrayList<MediaType> jsonTextTypes = new ArrayList<>();
        jsonTextTypes.add(MediaType.TEXT_HTML);
        jsonTextTypes.add(MediaType.APPLICATION_JSON);
        jsonConverter.setSupportedMediaTypes(jsonTextTypes);
        converters.add(jsonConverter);
        return converters;
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.setMessageConverters(createMessageConverters());
        return handlerAdapter;
    }

    //Spring 可以有两种方式处理静态文件：
    //    1、转发到默认的 web 服务器的 servlet 处理（比如 tomcat 来处理）
    //    2、使用 Spring ResourceHandler 处理
    //使用这两种办法都需要继承 WebMvcConfigurerAdapter 基类，覆盖其中相应的方法实现。

    /**
     * 默认Servlet处理
     * 如此配置后，如果 Spring 遇到没有 mapping 的 url 地址，就会转发到默认的 Servlet 处理（如 tomcat）。这其中就包括静态文件（前提是你没有为静态文件设置 RequestMapping）。
     * @param configurer
     */
    /*@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }*/

    /**
     * Spring ResourceHandler 处理
     * 为 url 地址符合 /assets/** 的文件设置了指定的文件路径，spring 会按照配置的先后顺序在指定的路径中查找文件是否存在并返回。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
     .addResourceLocations("/assets/","classpath:/assets/")
     .setCachePeriod(31556926);
     }
}
