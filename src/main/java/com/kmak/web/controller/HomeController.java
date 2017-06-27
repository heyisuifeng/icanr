package com.kmak.web.controller;

import com.kmak.entity.Book;
import com.kmak.entity.business.User;
import com.kmak.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/sendxml")
    @ResponseBody
    public void getxmlparam(@RequestBody Book book){
        System.out.print(book.getId());
        Book book1 = new Book();
        BeanUtils.copyProperties(book,book1);
    }

    @RequestMapping(value = "/ueditor")
    public String ueditor(){
        return "ueditor";
    }
}
