package com.kmak.controller;

import com.kmak.pojo.User;
import com.kmak.service.LoginService;
import com.kmak.serviceImpl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Leaf.Ye on 2017/3/1.
 */
@Controller
public class HomeController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("password"));
        boolean flag = loginService.login(user);
        if(!flag)
            return "error";
        return "login";
    }
}
