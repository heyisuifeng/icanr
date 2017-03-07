package com.kmak.entity.requestEntity;

import com.kmak.entity.business.Role;
import com.kmak.entity.business.User;

import java.util.List;

/**
 * Created by Leaf.Ye on 2017/3/7.
 */
public class TestTransactionalEntity {

    private List<User> users;

    private List<Role> roles;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
