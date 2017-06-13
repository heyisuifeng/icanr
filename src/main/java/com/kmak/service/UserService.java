package com.kmak.service;

import com.kmak.entity.business.User;
import com.kmak.entity.requestEntity.TestTransactionalEntity;

/**
 * Created by Leaf.Ye on 2017/3/7.
 */
public interface UserService {

    public void addUser(User user) throws Exception;

    public void addUserAndRole(TestTransactionalEntity entity);

    public void deleteUserById(int id);
}
