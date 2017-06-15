package com.kmak.web.controller;

import com.kmak.entity.business.Role;
import com.kmak.entity.business.User;
import com.kmak.entity.requestEntity.TestTransactionalEntity;
import com.kmak.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leaf.Ye on 2017/3/7.
 */
@Controller
public class UserController {

    @Inject
    private UserService userService;

    @RequestMapping("/addUser")
    public String addUser()throws Exception{
        User user = new User();
        user.setName("adduser");
        user.setPassword("adduser");
        userService.addUser(user);
        return "success";
    }

    @RequestMapping("/testTran")
    public String testTran(){
        TestTransactionalEntity entity = new TestTransactionalEntity();
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setName("adduser");
        user.setPassword("adduser");
        users.add(user);
        entity.setUsers(users);
        List<Role> roles = new ArrayList<Role>();
        Role role = new Role();
        //role.setId(1);
        role.setRoleName("testTran");
        role.setDescription("test");
        roles.add(role);
        entity.setRoles(roles);
        userService.addUserAndRole(entity);
        return "success";
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id)throws Exception{
        userService.deleteUserById(id);
        return "success";
    }
}
