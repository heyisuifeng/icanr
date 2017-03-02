package com.kmak;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by 1 on 2017/3/1.
 */
public class WebConfig implements WebApplicationInitializer{
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher",new DispatcherServlet(rootContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
