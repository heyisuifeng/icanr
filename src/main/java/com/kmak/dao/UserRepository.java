package com.kmak.dao;

import com.kmak.entity.business.User;

/**
 * Created by 1 on 2017/3/2.
 */
public interface UserRepository {
    public boolean login(User user);

    public void addUser(User user);
}
