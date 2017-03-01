package com.kmak.controller;

import com.sun.net.httpserver.HttpServer;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 1 on 2017/3/1.
 */
@Controller
public class HomeController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        String url = request.getRequestURI();
        return "login";
    }
}
